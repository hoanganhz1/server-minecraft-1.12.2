#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
OnePiece Skyblock RPG - Unified Resource Pack Pipeline (MC 1.12.2)
Phase 13 Integration:
- Base Layer: Classic Faithful 64x64 (pack_format: 3)
- Retain 100% original .png names from Mine Mine no Mi & trangbilq
- Standardize all 2D textures to 64x64 RGBA power-of-2 (Nearest-Neighbor, frame 0 if animated)
- 100% Solid Material Atlas (alpha = 255) for all 3D Voxel Models (Zero transparent holes)
- Robust make_cube() ensuring from < to, thickness >= 0.2, angles in {-45, -22.5, 0, 22.5, 45}
- Master 2-Phased Predicates on diamond_hoe.json:
    * PK1 (ID 1 - 99): 41 active server items & 3D entity rigs
    * PK2 (ID 100 - 1550): 937 full catalog items & abilities from Mine Mine no Mi & trangbilq
- Generates plugins/Skript/rp_models.txt for fast O(1) reflection lookup in Skript
- Generates RESOURCEPACK_ASSET_MAPPING.md comprehensive manual
- Audit with 48 validation gates and auto SHA-1 sync to server.properties & README.md
"""

import os
import sys
import json
import zlib
import struct
import hashlib
import zipfile
import math
import shutil
import unicodedata
import re
from PIL import Image, ImageDraw, ImageFont

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
ROOT_DIR = os.path.abspath(os.path.join(BASE_DIR, ".."))
RP_DIR = os.path.join(BASE_DIR, "OnePieceSkyblock_RP")
ASSETS_DIR = os.path.join(RP_DIR, "assets", "minecraft")
MODELS_ITEM_DIR = os.path.join(ASSETS_DIR, "models", "item")
MODELS_CUSTOM_DIR = os.path.join(MODELS_ITEM_DIR, "custom")
MODELS_MOD_DIR = os.path.join(MODELS_ITEM_DIR, "mineminenomi")
MODELS_LQ_DIR = os.path.join(MODELS_ITEM_DIR, "trangbilq")
TEXTURES_DIR = os.path.join(ASSETS_DIR, "textures")
TEXTURES_CUSTOM_DIR = os.path.join(TEXTURES_DIR, "items", "custom")
TEXTURES_MOD_DIR = os.path.join(TEXTURES_DIR, "items", "mineminenomi")
TEXTURES_LQ_DIR = os.path.join(TEXTURES_DIR, "items", "trangbilq")
OUTPUT_ZIP = os.path.join(BASE_DIR, "OnePieceSkyblock_RP.zip")
MOD_SOURCE_DIR = os.path.join(ROOT_DIR, "mod_source")
SERVER_PROPERTIES = os.path.join(ROOT_DIR, "server.properties")
README_MD = os.path.join(ROOT_DIR, "README.md")
MAPPING_MD = os.path.join(ROOT_DIR, "RESOURCEPACK_ASSET_MAPPING.md")
SKRIPT_DIR = os.path.join(ROOT_DIR, "plugins", "Skript")
RP_MODELS_TXT = os.path.join(SKRIPT_DIR, "rp_models.txt")
ENABLE_CUSTOM_FONT = False

# ---------------------------------------------------------------------
# 1. PURE PYTHON PNG ENCODER, DECODER & SCALER
# ---------------------------------------------------------------------

def create_png_bytes(width, height, pixels):
    """Encodes list of [r, g, b, a] pixels (length width*height) into an 8-bit RGBA PNG."""
    raw_rows = []
    for y in range(height):
        row = bytearray([0])  # filter type 0: None
        row_start = y * width
        for x in range(width):
            r, g, b, a = pixels[row_start + x]
            row.extend([r, g, b, a])
        raw_rows.append(bytes(row))

    raw_data = b"".join(raw_rows)
    compressed = zlib.compress(raw_data, 9)

    def png_chunk(chunk_type, data):
        c = chunk_type + data
        crc = struct.pack(">I", zlib.crc32(c) & 0xffffffff)
        return struct.pack(">I", len(data)) + c + crc

    out = [b"\x89PNG\r\n\x1a\n"]
    out.append(png_chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)))
    out.append(png_chunk(b"IDAT", compressed))
    out.append(png_chunk(b"IEND", b""))
    return b"".join(out)


def decode_png(filepath):
    """Decodes PNG into (width, height, pixels) where pixels is list of [r, g, b, a]."""
    try:
        with open(filepath, "rb") as f:
            data = f.read()
    except Exception:
        return None

    if len(data) < 24 or data[:8] != b"\x89PNG\r\n\x1a\n":
        return None

    pos = 8
    width, height = 0, 0
    idat = bytearray()
    palette = []
    trns = []
    color_type = 6

    while pos < len(data):
        chunk_len = struct.unpack(">I", data[pos:pos+4])[0]
        pos += 4
        chunk_type = data[pos:pos+4]
        pos += 4
        chunk_data = data[pos:pos+chunk_len]
        pos += chunk_len + 4  # skip data + crc

        if chunk_type == b"IHDR":
            width, height, bit_depth, color_type, comp, filt, inter = struct.unpack(">IIBBBBB", chunk_data)
            if bit_depth != 8 or comp != 0 or filt != 0 or inter != 0:
                return None
        elif chunk_type == b"PLTE":
            for i in range(0, len(chunk_data), 3):
                palette.append(list(chunk_data[i:i+3]))
        elif chunk_type == b"tRNS":
            trns = list(chunk_data)
        elif chunk_type == b"IDAT":
            idat.extend(chunk_data)
        elif chunk_type == b"IEND":
            break

    try:
        raw = zlib.decompress(bytes(idat))
    except Exception:
        return None

    if color_type == 6:   # RGBA
        bpp = 4
    elif color_type == 2: # RGB
        bpp = 3
    elif color_type == 3: # Indexed
        bpp = 1
    elif color_type == 0: # Grayscale
        bpp = 1
    elif color_type == 4: # Grayscale + Alpha
        bpp = 2
    else:
        return None

    stride = width * bpp
    pixels = []
    prev_row = bytearray(stride)
    raw_pos = 0

    def paeth(a, b, c):
        p = a + b - c
        pa = abs(p - a)
        pb = abs(p - b)
        pc = abs(p - c)
        if pa <= pb and pa <= pc: return a
        elif pb <= pc: return b
        else: return c

    for y in range(height):
        if raw_pos >= len(raw):
            break
        ftype = raw[raw_pos]
        raw_pos += 1
        curr_row = bytearray(raw[raw_pos:raw_pos+stride])
        raw_pos += stride
        recon = bytearray(stride)

        for i in range(len(curr_row)):
            left = recon[i - bpp] if i >= bpp else 0
            up = prev_row[i] if i < len(prev_row) else 0
            left_up = prev_row[i - bpp] if (i >= bpp and i - bpp < len(prev_row)) else 0

            if ftype == 0:
                recon[i] = curr_row[i]
            elif ftype == 1:
                recon[i] = (curr_row[i] + left) & 0xff
            elif ftype == 2:
                recon[i] = (curr_row[i] + up) & 0xff
            elif ftype == 3:
                recon[i] = (curr_row[i] + ((left + up) >> 1)) & 0xff
            elif ftype == 4:
                recon[i] = (curr_row[i] + paeth(left, up, left_up)) & 0xff
            else:
                recon[i] = curr_row[i]

        prev_row = recon

        for x in range(width):
            if color_type == 6:
                pixels.append(list(recon[x*4:x*4+4]))
            elif color_type == 2:
                rgb = list(recon[x*3:x*3+3])
                pixels.append(rgb + [255])
            elif color_type == 3:
                idx = recon[x]
                if idx < len(palette):
                    rgb = list(palette[idx])
                    alpha = 255
                    if trns and idx < len(trns):
                        alpha = trns[idx]
                    pixels.append(rgb + [alpha])
                else:
                    pixels.append([0, 0, 0, 0])
            elif color_type == 0:
                v = recon[x]
                pixels.append([v, v, v, 255])
            elif color_type == 4:
                v = recon[x*2]
                a = recon[x*2+1]
                pixels.append([v, v, v, a])

    return width, height, pixels


def process_2d_png(src_path, target_size=64):
    """
    Decodes PNG, crops vertical animation strips to top square frame,
    and resizes to target_size x target_size RGBA power-of-2 with nearest-neighbor sampling.
    """
    res = decode_png(src_path)
    if not res:
        return None
    src_w, src_h, pixels = res
    if src_h > src_w:
        src_h = src_w
        pixels = pixels[:src_w * src_w]
    elif src_w > src_h:
        new_pixels = []
        for y in range(src_h):
            row_start = y * src_w
            new_pixels.extend(pixels[row_start : row_start + src_h])
        src_w = src_h
        pixels = new_pixels

    if src_w == target_size and src_h == target_size:
        return create_png_bytes(target_size, target_size, pixels)

    scaled_pixels = []
    for ty in range(target_size):
        sy = int(ty * src_h / target_size)
        sy = min(src_h - 1, max(0, sy))
        row_offset = sy * src_w
        for tx in range(target_size):
            sx = int(tx * src_w / target_size)
            sx = min(src_w - 1, max(0, sx))
            scaled_pixels.append(pixels[row_offset + sx])

    return create_png_bytes(target_size, target_size, scaled_pixels)


def generate_hd_pixel_art(width, height, category, primary_rgb, secondary_rgb, accent_rgb):
    """Procedural fallback pixel art generator."""
    pixels = []
    cx, cy = width // 2, height // 2
    r_max = width // 2 - 2

    for y in range(height):
        for x in range(width):
            dist = math.hypot(x - cx, y - cy)
            if dist <= r_max:
                if dist >= r_max - 1.5:
                    pixels.append(list(secondary_rgb) + [255])
                elif abs(x - cx) <= 2 or abs(y - cy) <= 2:
                    pixels.append(list(accent_rgb) + [255])
                else:
                    pixels.append(list(primary_rgb) + [255])
            else:
                pixels.append([0, 0, 0, 0])

    return create_png_bytes(width, height, pixels)


# ---------------------------------------------------------------------
# 2. 100% SOLID MATERIAL ATLAS GENERATOR FOR 3D MODELS (NO HOLES)
# ---------------------------------------------------------------------

UV_PRIMARY   = [0.0,  0.0, 4.0,  4.0]
UV_SECONDARY = [4.0,  0.0, 8.0,  4.0]
UV_ACCENT    = [8.0,  0.0, 12.0, 4.0]
UV_WHITE     = [12.0, 0.0, 16.0, 4.0]
UV_RED       = [0.0,  4.0, 4.0,  8.0]
UV_DARK      = [4.0,  4.0, 8.0,  8.0]
UV_GOLD      = [8.0,  4.0, 12.0, 8.0]
UV_GLOW      = [12.0, 4.0, 16.0, 8.0]

def generate_3d_material_atlas(palette):
    """
    Builds a 64x64 material atlas with 100% OPAQUE pixels (alpha = 255 on all 4096 pixels).
    Zero transparent pixels -> Zero holes in 3D voxel models!
    """
    width, height = 64, 64
    pixels = []

    grid_colors = {
        (0, 0): palette["primary"],
        (1, 0): palette["secondary"],
        (2, 0): palette["accent"],
        (3, 0): palette.get("white", (245, 245, 240)),
        (0, 1): palette.get("red", (220, 30, 30)),
        (1, 1): palette.get("dark", (35, 35, 40)),
        (2, 1): palette.get("gold", (240, 190, 40)),
        (3, 1): palette.get("glow", (160, 45, 215)),
        # Row 2 (V: 8..12): Shaded and highlighted primary & secondary
        (0, 2): tuple(max(0, c - 30) for c in palette["primary"]),
        (1, 2): tuple(min(255, c + 35) for c in palette["primary"]),
        (2, 2): tuple(max(0, c - 25) for c in palette["secondary"]),
        (3, 2): tuple(min(255, c + 35) for c in palette["secondary"]),
        # Row 3 (V: 12..16): Shaded accent & gold variations
        (0, 3): tuple(max(0, c - 30) for c in palette["accent"]),
        (1, 3): tuple(min(255, c + 35) for c in palette["accent"]),
        (2, 3): tuple(max(0, c - 30) for c in palette.get("gold", (240, 190, 40))),
        (3, 3): tuple(min(255, c + 40) for c in palette.get("glow", (160, 45, 215))),
    }

    for y in range(height):
        cell_y = y // 16
        in_cell_y = y % 16
        for x in range(width):
            cell_x = x // 16
            in_cell_x = x % 16

            base_rgb = grid_colors.get((cell_x, cell_y), palette["primary"])
            grain = (((in_cell_x * 5 + in_cell_y * 11) % 7) - 3) * 3
            if in_cell_x == 0 or in_cell_y == 0:
                grain -= 8

            r = max(0, min(255, base_rgb[0] + grain))
            g = max(0, min(255, base_rgb[1] + grain))
            b = max(0, min(255, base_rgb[2] + grain))
            pixels.append([r, g, b, 255])  # 100% Solid

    return create_png_bytes(width, height, pixels)


# ---------------------------------------------------------------------
# 3. MATHEMATICAL CUBE BUILDER HELPER (make_cube)
# ---------------------------------------------------------------------

def make_cube(name, x1, y1, z1, x2, y2, z2, uv_rect, rot_axis=None, rot_angle=0.0, rot_origin=None):
    fx1, fx2 = min(x1, x2), max(x1, x2)
    fy1, fy2 = min(y1, y2), max(y1, y2)
    fz1, fz2 = min(z1, z2), max(z1, z2)

    if fx2 - fx1 < 0.2: fx2 = round(fx1 + 0.2, 3)
    if fy2 - fy1 < 0.2: fy2 = round(fy1 + 0.2, 3)
    if fz2 - fz1 < 0.2: fz2 = round(fz1 + 0.2, 3)

    fx1 = max(-16.0, min(32.0, round(fx1, 3)))
    fy1 = max(-16.0, min(32.0, round(fy1, 3)))
    fz1 = max(-16.0, min(32.0, round(fz1, 3)))
    fx2 = max(-16.0, min(32.0, round(fx2, 3)))
    fy2 = max(-16.0, min(32.0, round(fy2, 3)))
    fz2 = max(-16.0, min(32.0, round(fz2, 3)))

    cube = {
        "name": name,
        "from": [fx1, fy1, fz1],
        "to": [fx2, fy2, fz2],
        "faces": {
            "north": {"uv": uv_rect, "texture": "#texture"},
            "south": {"uv": uv_rect, "texture": "#texture"},
            "east":  {"uv": uv_rect, "texture": "#texture"},
            "west":  {"uv": uv_rect, "texture": "#texture"},
            "up":    {"uv": uv_rect, "texture": "#texture"},
            "down":  {"uv": uv_rect, "texture": "#texture"}
        }
    }

    if rot_axis and rot_angle != 0.0:
        VALID_ANGLES = [-45.0, -22.5, 0.0, 22.5, 45.0]
        snapped_angle = min(VALID_ANGLES, key=lambda a: abs(a - rot_angle))
        origin = rot_origin if rot_origin is not None else [8.0, 8.0, 8.0]
        cube["rotation"] = {
            "origin": [round(origin[0], 2), round(origin[1], 2), round(origin[2], 2)],
            "axis": rot_axis,
            "angle": snapped_angle
        }

    return cube

def generate_geometry_seaking(state="idle"):
    """
    Siêu Boss Vua Biển 3D Rig (35 solid connected elements).
    - Đốt đuôi & vây đuôi, thân bụng & giáp bụng (offset 0.1 chống Z-fight)
    - Thân ngực & giáp ngực, đốt cổ
    - 6 gai vây lưng sắc nhọn, 2 vây mang xòe rộng
    - Hộp sọ, chân mày, 2 mắt đỏ nổi, 2 sừng, vòm miệng, lưỡi đỏ, hàm dưới
    - 10 răng nanh trắng sắc nhọn
    """
    elements = []

    # 1. Khối Đuôi & Thân Dưới (4 khối)
    elements.append(make_cube("tail_rear",     5.5, 0.0, 11.0, 10.5, 3.5, 16.0, UV_PRIMARY))
    elements.append(make_cube("tail_fin",      4.0, 0.5, 15.5, 12.0, 3.0, 18.5, UV_ACCENT))
    elements.append(make_cube("body_lower",    4.5, 0.0, 5.0,  11.5, 5.0, 11.5, UV_PRIMARY))
    elements.append(make_cube("belly_lower",   4.9, 0.1, 4.8,  11.1, 4.5, 11.0, UV_SECONDARY))

    # 2. Khối Thân Giữa & Cổ Dựng Đứng (3 khối)
    elements.append(make_cube("body_chest",    4.5, 4.5, 4.5,  11.5, 12.0, 10.0, UV_PRIMARY))
    elements.append(make_cube("chest_armor",   5.0, 4.8, 4.2,  11.0, 11.8, 5.0,  UV_SECONDARY))
    elements.append(make_cube("neck_upper",    4.8, 11.5, 4.0, 11.2, 17.5, 9.5,  UV_PRIMARY))

    # 3. Vây Lưng (6 gai vây) & 2 Vây Mang
    elements.append(make_cube("spine_1",       7.3, 2.5, 13.0, 8.7, 5.0, 15.0, UV_ACCENT))
    elements.append(make_cube("spine_2",       7.3, 5.0, 9.8,  8.7, 8.0, 12.0, UV_ACCENT))
    elements.append(make_cube("spine_3",       7.3, 9.0, 9.8,  8.7, 12.0, 12.0, UV_ACCENT))
    elements.append(make_cube("spine_4",       7.3, 13.0, 9.5, 8.7, 16.0, 11.8, UV_ACCENT))
    elements.append(make_cube("spine_5",       7.3, 17.0, 9.0, 8.7, 20.0, 11.2, UV_ACCENT))
    elements.append(make_cube("spine_6",       7.3, 21.0, 3.0, 8.7, 24.5, 5.0,  UV_ACCENT))

    elements.append(make_cube("fin_left",      1.0, 13.0, 5.5, 4.8, 17.5, 7.0, UV_ACCENT, rot_axis="y", rot_angle=-22.5, rot_origin=[4.8, 15.0, 6.0]))
    elements.append(make_cube("fin_right",     11.2, 13.0, 5.5, 15.0, 17.5, 7.0, UV_ACCENT, rot_axis="y", rot_angle=22.5, rot_origin=[11.2, 15.0, 6.0]))

    # 4. Hộp Sọ, Mắt, Sừng, Vòm Miệng & Răng Nanh
    upper_rot = 22.5 if state in ("attack", "roar") else 0.0
    lower_rot = -22.5 if state in ("attack", "roar") else 0.0
    skull_orig = [8.0, 17.0, 7.0]
    jaw_orig = [8.0, 15.0, 7.0]

    elements.append(make_cube("skull_upper",   3.8, 16.5, 0.0, 12.2, 20.5, 8.5, UV_PRIMARY, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("brow_left",     4.2, 20.2, 0.0, 6.8, 21.2, 4.0, UV_PRIMARY, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("brow_right",    9.2, 20.2, 0.0, 11.8, 21.2, 4.0, UV_PRIMARY, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("eye_left",      3.5, 18.0, 2.0, 4.2, 19.8, 4.5, UV_RED, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("eye_right",     11.8, 18.0, 2.0, 12.5, 19.8, 4.5, UV_RED, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("horn_left",     4.5, 20.0, 4.0, 6.0, 24.0, 9.0, UV_GOLD, rot_axis="x", rot_angle=-22.5 + upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("horn_right",    10.0, 20.0, 4.0, 11.5, 24.0, 9.0, UV_GOLD, rot_axis="x", rot_angle=-22.5 + upper_rot, rot_origin=skull_orig))

    elements.append(make_cube("palate_dark",   4.8, 15.2, 0.5, 11.2, 16.5, 7.5, UV_DARK, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))

    # Upper Teeth (6 teeth)
    elements.append(make_cube("tooth_u1",      4.3, 15.2, 0.1, 5.1, 16.6, 0.9, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("tooth_u2",      6.2, 15.2, 0.1, 7.0, 16.6, 0.9, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("tooth_u3",      9.0, 15.2, 0.1, 9.8, 16.6, 0.9, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("tooth_u4",      10.9, 15.2, 0.1, 11.7, 16.6, 0.9, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("tooth_u_side1", 4.1, 15.2, 2.0, 4.9, 16.6, 2.8, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))
    elements.append(make_cube("tooth_u_side2", 11.1, 15.2, 2.0, 11.9, 16.6, 2.8, UV_WHITE, rot_axis="x", rot_angle=upper_rot, rot_origin=skull_orig))

    # Lower Jaw, Tongue & Lower Teeth (4 teeth)
    elements.append(make_cube("jaw_lower",     4.2, 13.5, 0.2, 11.8, 15.2, 8.0, UV_SECONDARY, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))
    elements.append(make_cube("tongue_red",    5.8, 15.0, 1.0, 10.2, 15.8, 6.5, UV_RED, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))
    elements.append(make_cube("tooth_l1",      5.2, 15.0, 0.3, 6.0, 16.5, 1.1, UV_WHITE, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))
    elements.append(make_cube("tooth_l2",      10.0, 15.0, 0.3, 10.8, 16.5, 1.1, UV_WHITE, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))
    elements.append(make_cube("tooth_l_side1", 4.5, 15.0, 2.2, 5.3, 16.5, 3.0, UV_WHITE, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))
    elements.append(make_cube("tooth_l_side2", 10.7, 15.0, 2.2, 11.5, 16.5, 3.0, UV_WHITE, rot_axis="x", rot_angle=lower_rot, rot_origin=jaw_orig))

    return elements


def generate_geometry_dummy(variant="normal"):
    """
    Combat Dummy 3D Model (23 - 27 solid connected elements).
    - Chân đế chữ thập & chốt sắt, cột trụ gỗ
    - Thân rơm, đai da, hồng tâm nổi hẳn ra ngoài chống Z-fight
    - Xà ngang tay, giáp vai, kiếm gỗ bên hông
    - Đầu rơm, Mũ Rơm Hải Tặc 3D (Vành mũ, chóp mũ, dải băng đỏ)
    - Boss có sừng, Logia có tinh thể bay
    """
    elements = [
        # Chân đế gỗ chữ thập (offset 0.05 ở thanh Z để không Z-fighting tại tâm)
        make_cube("base_beam_x",   2.0, 0.0, 6.5, 14.0, 1.5, 9.5, UV_SECONDARY),
        make_cube("base_beam_z",   6.5, 0.0, 2.0, 9.5, 1.55, 14.0, UV_SECONDARY),

        # 4 Chốt sắt giữ đế
        make_cube("peg_1",         1.8, 0.0, 6.3, 3.2, 2.5, 9.7, UV_DARK),
        make_cube("peg_2",         12.8, 0.0, 6.3, 14.2, 2.5, 9.7, UV_DARK),
        make_cube("peg_3",         6.3, 0.0, 1.8, 9.7, 2.5, 3.2, UV_DARK),
        make_cube("peg_4",         6.3, 0.0, 12.8, 9.7, 2.5, 14.2, UV_DARK),

        # Cột trụ chính & Thân bọc rơm
        make_cube("main_pole",     6.8, 1.5, 6.8, 9.2, 14.0, 9.2, UV_SECONDARY),
        make_cube("torso_straw",   4.5, 5.5, 5.2, 11.5, 13.5, 10.8, UV_PRIMARY),
        make_cube("leather_belt",  4.3, 6.2, 5.0, 11.7, 7.6, 11.0, UV_DARK),

        # Bia Hồng Tâm trước ngực (Nổi hẳn ra ngoài Z = 4.6..5.2 chống Z-fight)
        make_cube("target_white",  6.0, 8.0, 4.8, 10.0, 12.0, 5.2, UV_WHITE),
        make_cube("target_red",    7.0, 9.0, 4.6, 9.0, 11.0, 4.9, UV_RED),

        # Xà ngang 2 tay & Giáp vai
        make_cube("arm_crossbar",  1.0, 10.5, 6.8, 15.0, 12.5, 9.2, UV_SECONDARY),
        make_cube("arm_cuff_l",    1.0, 10.3, 6.6, 2.5, 12.7, 9.4, UV_DARK),
        make_cube("arm_cuff_r",    13.5, 10.3, 6.6, 15.0, 12.7, 9.4, UV_DARK),
        make_cube("shoulder_l",    1.5, 11.8, 5.8, 4.8, 14.0, 10.2, UV_ACCENT),
        make_cube("shoulder_r",    11.2, 11.8, 5.8, 14.5, 14.0, 10.2, UV_ACCENT),

        # Thanh kiếm gỗ bên hông
        make_cube("sword_blade",   3.2, 2.0, 9.5, 4.2, 11.0, 10.5, UV_WHITE),
        make_cube("sword_guard",   2.2, 11.0, 9.0, 5.2, 11.8, 11.0, UV_GOLD),
        make_cube("sword_hilt",    3.2, 11.8, 9.5, 4.2, 14.0, 10.5, UV_DARK),

        # Đầu bù nhìn & Chiếc Mũ Rơm 3D
        make_cube("head_core",     5.2, 13.5, 5.2, 10.8, 18.5, 10.8, UV_PRIMARY),
        make_cube("hat_brim",      3.0, 17.8, 3.0, 13.0, 18.8, 13.0, UV_GOLD),
        make_cube("hat_crown",     5.0, 18.8, 5.0, 11.0, 21.0, 11.0, UV_GOLD),
        make_cube("hat_ribbon",    4.8, 18.8, 4.8, 11.2, 19.6, 11.2, UV_RED),
    ]

    if variant == "boss":
        elements.append(make_cube("horn_l", 3.5, 19.0, 7.0, 5.0, 23.0, 9.0, UV_DARK))
        elements.append(make_cube("horn_r", 11.0, 19.0, 7.0, 12.5, 23.0, 9.0, UV_DARK))
    elif variant == "logia":
        elements.append(make_cube("crystal_1", -1.0, 11.5, 4.0, 1.0, 13.5, 6.0, UV_GLOW))
        elements.append(make_cube("crystal_2", 15.0, 11.5, 4.0, 17.0, 13.5, 6.0, UV_GLOW))
        elements.append(make_cube("crystal_3", 2.0, 14.5, 11.5, 4.0, 16.5, 13.5, UV_GLOW))
        elements.append(make_cube("crystal_4", 12.0, 14.5, 11.5, 14.0, 16.5, 13.5, UV_GLOW))

    return elements


def generate_geometry_flag():
    """Pirate Crew 3D Flag Rig (18 solid connected elements with floating wind curve)."""
    elements = [
        make_cube("pole_base",     7.2, 0.0, 7.2, 8.8, 10.0, 8.8, UV_SECONDARY),
        make_cube("pole_mid",      7.2, 10.0, 7.2, 8.8, 18.0, 8.8, UV_SECONDARY),
        make_cube("pole_top",      7.2, 18.0, 7.2, 8.8, 22.0, 8.8, UV_SECONDARY),

        make_cube("spear_point",   6.8, 22.0, 6.8, 9.2, 24.5, 9.2, UV_GOLD),
        make_cube("spear_cross",   5.5, 21.5, 7.5, 10.5, 22.5, 8.5, UV_GOLD),
        make_cube("cross_bar",     1.0, 18.5, 7.5, 15.0, 19.5, 8.5, UV_SECONDARY),

        # 3 Dải Cờ Vải Liền Khối
        make_cube("cloth_seg_1",   1.5, 8.5, 7.6, 8.0, 18.5, 8.4, UV_ACCENT),
        make_cube("cloth_seg_2",   8.0, 8.5, 7.6, 14.5, 18.5, 8.4, UV_ACCENT, rot_axis="y", rot_angle=-22.5, rot_origin=[8.0, 13.5, 8.0]),
        make_cube("cloth_seg_3",   14.5, 8.5, 7.6, 18.5, 18.5, 8.4, UV_ACCENT, rot_axis="y", rot_angle=22.5, rot_origin=[14.5, 13.5, 8.0]),

        # 3 Dải Viền Dưới
        make_cube("cloth_fringe_1", 1.5, 6.5, 7.6, 8.0, 8.5, 8.4, UV_ACCENT),
        make_cube("cloth_fringe_2", 8.0, 6.5, 7.6, 14.5, 8.5, 8.4, UV_ACCENT, rot_axis="y", rot_angle=-22.5, rot_origin=[8.0, 7.5, 8.0]),
        make_cube("cloth_fringe_3", 14.5, 6.5, 7.6, 18.5, 8.5, 8.4, UV_ACCENT, rot_axis="y", rot_angle=22.5, rot_origin=[14.5, 7.5, 8.0]),

        # Phù Điêu Nổi Hai Mặt Trước - Sau (Lệch 0.1 chống Z-fighting)
        make_cube("emblem_front",  3.5, 11.5, 7.4, 7.5, 15.5, 7.6, UV_WHITE),
        make_cube("emblem_back",   3.5, 11.5, 8.4, 7.5, 15.5, 8.6, UV_WHITE),
        make_cube("crossbones_f1", 3.0, 12.5, 7.2, 8.0, 14.5, 7.4, UV_WHITE, rot_axis="z", rot_angle=22.5, rot_origin=[5.5, 13.5, 7.3]),
        make_cube("crossbones_f2", 3.0, 12.5, 7.2, 8.0, 14.5, 7.4, UV_WHITE, rot_axis="z", rot_angle=-22.5, rot_origin=[5.5, 13.5, 7.3]),
        make_cube("crossbones_b1", 3.0, 12.5, 8.6, 8.0, 14.5, 8.8, UV_WHITE, rot_axis="z", rot_angle=22.5, rot_origin=[5.5, 13.5, 8.7]),
        make_cube("crossbones_b2", 3.0, 12.5, 8.6, 8.0, 14.5, 8.8, UV_WHITE, rot_axis="z", rot_angle=-22.5, rot_origin=[5.5, 13.5, 8.7]),
    ]
    return elements


def generate_geometry_chest():
    """Rương Trái Ác Quỷ 3D (16 solid connected elements)."""
    lid_orig = [8.0, 10.0, 12.0]
    elements = [
        make_cube("chest_body",   3.0, 4.0, 4.0, 13.0, 10.0, 12.0, UV_PRIMARY),

        # 4 Viền Góc Vàng
        make_cube("bracket_fl",   2.8, 4.0, 3.8, 4.5, 10.1, 5.0, UV_GOLD),
        make_cube("bracket_fr",   11.5, 4.0, 3.8, 13.2, 10.1, 5.0, UV_GOLD),
        make_cube("bracket_bl",   2.8, 4.0, 11.0, 4.5, 10.1, 12.2, UV_GOLD),
        make_cube("bracket_br",   11.5, 4.0, 11.0, 13.2, 10.1, 12.2, UV_GOLD),

        # Ổ Khóa Vàng & Khe Khóa
        make_cube("lock_plate",   6.8, 6.0, 3.7, 9.2, 8.5, 4.1, UV_GOLD),
        make_cube("lock_keyhole", 7.5, 6.5, 3.5, 8.5, 7.8, 3.7, UV_DARK),

        # Nắp Rương Hé Mở 22.5 Độ
        make_cube("lid_top",      3.0, 10.0, 4.0, 13.0, 12.5, 12.0, UV_PRIMARY, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),
        make_cube("lid_front",    3.0, 9.8, 3.8, 13.0, 12.0, 5.0, UV_PRIMARY, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),
        make_cube("lid_back",     3.0, 9.8, 11.0, 13.0, 12.0, 12.2, UV_PRIMARY, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),
        make_cube("lid_left",     2.8, 9.8, 4.5, 4.2, 12.0, 11.5, UV_PRIMARY, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),
        make_cube("lid_right",    11.8, 9.8, 4.5, 13.2, 12.0, 11.5, UV_PRIMARY, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),
        make_cube("lid_rim_gold", 2.7, 9.6, 3.7, 13.3, 10.4, 12.3, UV_GOLD, rot_axis="x", rot_angle=22.5, rot_origin=lid_orig),

        # Quả Ác Quỷ Tím Phát Sáng Bên Trong
        make_cube("fruit_body",   6.0, 7.0, 6.0, 10.0, 11.5, 10.0, UV_GLOW),
        make_cube("fruit_stem",   7.3, 11.5, 7.3, 8.7, 13.5, 8.7, UV_GOLD),
        make_cube("fruit_curl",   6.5, 12.5, 7.3, 8.0, 13.5, 9.5, UV_GOLD),
    ]
    return elements


# ---------------------------------------------------------------------
# 5. PALETTES FOR 3D MODELS
# ---------------------------------------------------------------------

PALETTES_3D = {
    "df_chest": {
        "primary": (115, 75, 35),
        "secondary": (85, 52, 22),
        "accent": (145, 95, 45),
        "white": (245, 245, 240),
        "red": (210, 35, 35),
        "dark": (35, 30, 30),
        "gold": (235, 185, 35),
        "glow": (160, 45, 215)
    },
    "flag_dragon": {
        "primary": (95, 65, 35),
        "secondary": (120, 85, 45),
        "accent": (205, 30, 40),
        "white": (250, 250, 250),
        "red": (220, 30, 30),
        "dark": (30, 30, 35),
        "gold": (240, 195, 40),
        "glow": (255, 100, 50)
    },
    "flag_gold": {
        "primary": (95, 65, 35),
        "secondary": (120, 85, 45),
        "accent": (225, 180, 30),
        "white": (250, 250, 250),
        "red": (210, 35, 35),
        "dark": (30, 30, 35),
        "gold": (255, 220, 60),
        "glow": (255, 245, 180)
    },
    "flag_phoenix": {
        "primary": (95, 65, 35),
        "secondary": (120, 85, 45),
        "accent": (20, 180, 200),
        "white": (250, 250, 250),
        "red": (210, 35, 35),
        "dark": (30, 30, 35),
        "gold": (240, 195, 40),
        "glow": (180, 255, 255)
    },
    "seaking": {
        "primary": (22, 85, 130),
        "secondary": (45, 170, 185),
        "accent": (30, 145, 105),
        "white": (250, 250, 245),
        "red": (225, 30, 30),
        "dark": (25, 30, 40),
        "gold": (240, 200, 80),
        "glow": (70, 230, 255)
    },
    "dummy_normal": {
        "primary": (210, 170, 50),
        "secondary": (125, 85, 45),
        "accent": (100, 105, 115),
        "white": (250, 250, 250),
        "red": (220, 30, 30),
        "dark": (40, 35, 35),
        "gold": (240, 190, 40),
        "glow": (245, 245, 240)
    },
    "dummy_boss": {
        "primary": (175, 45, 35),
        "secondary": (85, 55, 35),
        "accent": (145, 25, 25),
        "white": (250, 250, 250),
        "red": (230, 20, 20),
        "dark": (25, 20, 25),
        "gold": (240, 190, 40),
        "glow": (255, 70, 40)
    },
    "dummy_logia": {
        "primary": (70, 130, 180),
        "secondary": (75, 95, 115),
        "accent": (115, 200, 240),
        "white": (250, 250, 250),
        "red": (220, 30, 30),
        "dark": (35, 40, 50),
        "gold": (240, 190, 40),
        "glow": (140, 230, 255)
    }
}






standard_3d_display_base = {
    "thirdperson_righthand": {
        "rotation": [ 0, 90, -35 ],
        "translation": [ 0, 1.25, -3.5 ],
        "scale": [ 0.85, 0.85, 0.85 ]
    },
    "thirdperson_lefthand": {
        "rotation": [ 0, -90, 35 ],
        "translation": [ 0, 1.25, -3.5 ],
        "scale": [ 0.85, 0.85, 0.85 ]
    },
    "firstperson_righthand": {
        "rotation": [ 0, -135, 25 ],
        "translation": [ 0, 4, 2 ],
        "scale": [ 1.7, 1.7, 1.7 ]
    },
    "firstperson_lefthand": {
        "rotation": [ 0, 45, -25 ],
        "translation": [ 0, 4, 2 ],
        "scale": [ 1.7, 1.7, 1.7 ]
    },
    "ground": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, 3, 0 ],
        "scale": [ 0.5, 0.5, 0.5 ]
    },
    "gui": {
        "rotation": [ 30, 225, 0 ],
        "translation": [ 0, 0, 0 ],
        "scale": [ 0.625, 0.625, 0.625 ]
    },
    "head": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, -9.5, 0 ],
        "scale": [ 2.2, 2.2, 2.2 ]
    },
    "fixed": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, 0, 0 ],
        "scale": [ 0.5, 0.5, 0.5 ]
    }
}



standard_3d_display_base = {
    "thirdperson_righthand": {
        "rotation": [ 0, 90, -35 ],
        "translation": [ 0, 1.25, -3.5 ],
        "scale": [ 0.85, 0.85, 0.85 ]
    },
    "thirdperson_lefthand": {
        "rotation": [ 0, -90, 35 ],
        "translation": [ 0, 1.25, -3.5 ],
        "scale": [ 0.85, 0.85, 0.85 ]
    },
    "firstperson_righthand": {
        "rotation": [ 0, -135, 25 ],
        "translation": [ 0, 4, 2 ],
        "scale": [ 1.7, 1.7, 1.7 ]
    },
    "firstperson_lefthand": {
        "rotation": [ 0, 45, -25 ],
        "translation": [ 0, 4, 2 ],
        "scale": [ 1.7, 1.7, 1.7 ]
    },
    "ground": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, 3, 0 ],
        "scale": [ 0.5, 0.5, 0.5 ]
    },
    "gui": {
        "rotation": [ 30, 225, 0 ],
        "translation": [ 0, 0, 0 ],
        "scale": [ 0.625, 0.625, 0.625 ]
    },
    "head": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, -9.5, 0 ],
        "scale": [ 2.2, 2.2, 2.2 ]
    },
    "fixed": {
        "rotation": [ 0, 0, 0 ],
        "translation": [ 0, 0, 0 ],
        "scale": [ 0.5, 0.5, 0.5 ]
    }
}

# ---------------------------------------------------------------------
# 5. HANDHELD / WEAPON CLASSIFIER & STRING HELPERS
# ---------------------------------------------------------------------

ARMOR_WORDS = {'hat', 'cap', 'helmet', 'shirt', 'coat', 'cape', 'pants', 'boots', 'shoes', 'gloves', 'glasses', 'ring', 'mask', 'belt', 'socks'}

WEAPON_TOKENS = {
    'sword', 'swords', 'blade', 'blades', 'katana', 'saber', 'sabers', 'knife', 'knives',
    'dagger', 'daggers', 'scythe', 'cutlass', 'rapier', 'hammer', 'hammers', 'axe', 'axes',
    'pickaxe', 'pickaxes', 'hoe', 'hoes', 'shovel', 'shovels', 'spear', 'spears', 'lance',
    'trident', 'staff', 'club', 'mace', 'bat', 'gun', 'guns', 'pistol', 'pistols', 'rifle',
    'rifles', 'cannon', 'cannons', 'bazooka', 'slingshot', 'bow', 'pipe', 'jitte', 'hook',
    'scissors', 'wand', 'harpoon', 'drill', 'saw', 'weapon', 'flintlock',
    'enma', 'shusui', 'kikoku', 'gryphon', 'murakumogiri', 'bisento', 'mogura', 'durandal',
    'yubashiri', 'shigure', 'raiu', 'kiribachi', 'kessui', 'warabide', 'napoleon',
    'thanhkiem', 'kiemmuramasa', 'nanhfenrir', 'songdao', 'quykiem', 'diet-than-cung',
    'chuy', 'kiem', 'dao', 'thuong', 'liem'
}

SPECIAL_WEAPONS = {
    'ace', 'ace_haki', 'wado_ichimonji', 'wado_ichimonji_haki', 'sandai_kitetsu', 'sandai_kitetsu_haki',
    'nidai_kitetsu', 'nidai_kitetsu_haki', 'shodai_kitetsu', 'ame_no_habakiri', 'ame_no_habakiri_haki',
    'soul_solid', 'nonosama_bo', 'ginga_pacifica', 'kabuto', 'kuro_kabuto', 'yoru', 'ama_no_murakumo'
}

def is_handheld_item(key, cat=''):
    k = key.lower()
    if cat in ('weapon', 'tool'):
        return True
    if cat in ('fruit', 'armor', 'icon', 'poster', 'chest', 'flag', 'scroll'):
        return False
    tokens = re.split(r'[_ -]', k)
    if any(t in ARMOR_WORDS for t in tokens):
        return False
    if 'no_mi' in k or k.endswith('_fruit'):
        return False
    if k.endswith('_dial') and k != 'axe_dial':
        return False
    for t in tokens:
        if t in WEAPON_TOKENS:
            return True
    if k in SPECIAL_WEAPONS:
        return True
    return False

def strip_prefix(s, prefix):
    if s.startswith(prefix):
        return s[len(prefix):]
    return s


# ---------------------------------------------------------------------
# 5B. MOD MODEL REGISTRY & EXACT DISPLAY GRIP RESOLVER
# ---------------------------------------------------------------------

MOD_MODELS_CACHE = {}

def load_mod_models_cache():
    global MOD_MODELS_CACHE
    if MOD_MODELS_CACHE:
        return MOD_MODELS_CACHE
    mod_models_dir = os.path.join(MOD_SOURCE_DIR, "mine-mine-no-mi-1.21.1-0.11.0.jar.src", "assets", "mineminenomi", "models")
    if not os.path.exists(mod_models_dir):
        return {}
    for root, dirs, files in os.walk(mod_models_dir):
        for f in files:
            if f.endswith(".json"):
                p = os.path.join(root, f)
                rel = os.path.relpath(p, mod_models_dir).replace(os.sep, "/")
                k = rel[:-5]
                try:
                    with open(p, "r", encoding="utf-8") as fp:
                        MOD_MODELS_CACHE[k] = json.load(fp)
                except Exception:
                    pass
    return MOD_MODELS_CACHE


def get_effective_mod_display(model_key, depth=0):
    cache = load_mod_models_cache()
    if depth > 5 or model_key not in cache:
        return None
    data = cache[model_key]
    if "display" in data:
        return data["display"]
    parent = data.get("parent", "")
    if parent.startswith("mineminenomi:"):
        parent = parent.split(":", 1)[1]
    if parent in cache:
        return get_effective_mod_display(parent, depth + 1)
    return None


def find_mod_display_for_key(key):
    cache = load_mod_models_cache()
    k_clean = key.lower()
    disp = get_effective_mod_display("item/" + k_clean)
    if disp:
        return disp
    for k in cache:
        if k.endswith("/" + k_clean) or k == k_clean:
            disp = get_effective_mod_display(k)
            if disp:
                return disp
    return None


def get_mod_blockbench_elements(key, orig_name=None):
    cache = load_mod_models_cache()
    keys_to_test = []
    if orig_name:
        base_orig = orig_name[:-4] if orig_name.lower().endswith(".png") else orig_name
        keys_to_test.append(base_orig.lower())
    keys_to_test.append(key.lower())

    for tk in keys_to_test:
        data = cache.get("item/" + tk) or cache.get(tk)
        if data and "elements" in data:
            return data
    return None


# Anime Weapon Archetype Displays (Exact Minecraft matrices matching mod weapons)
KATANA_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 0, -90, 55 ], "translation": [ 0, 11.5, -1.0 ], "scale": [ 2.0, 2.0, 1.0 ] },
    "thirdperson_lefthand":  { "rotation": [ 0, 90, -55 ], "translation": [ 0, 11.5, -1.0 ], "scale": [ 2.0, 2.0, 1.0 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

GREATSWORD_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 15, -90, 55 ], "translation": [ 0, 18.0, 2.5 ], "scale": [ 2.5, 2.5, 1.0 ] },
    "thirdperson_lefthand":  { "rotation": [ 15, 90, -55 ], "translation": [ 0, 18.0, 2.5 ], "scale": [ 2.5, 2.5, 1.0 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ],  "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ],  "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

POLEARM_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 0, -90, 5 ],  "translation": [ 0, 7.5, 8.0 ], "scale": [ 3.6, 3.6, 3.6 ] },
    "thirdperson_lefthand":  { "rotation": [ 0, 90, -5 ],  "translation": [ 0, 7.5, 8.0 ], "scale": [ 3.6, 3.6, 3.6 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

DAGGER_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 0, -90, 55 ], "translation": [ 0, 8.0, -0.5 ], "scale": [ 1.6, 1.6, 1.0 ] },
    "thirdperson_lefthand":  { "rotation": [ 0, 90, -55 ], "translation": [ 0, 8.0, -0.5 ], "scale": [ 1.6, 1.6, 1.0 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

BOW_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ -80.0, 260.0, -40.0 ], "translation": [ -1.0, -2.0, 2.5 ], "scale": [ 1.3, 1.3, 1.1 ] },
    "thirdperson_lefthand":  { "rotation": [ -80.0, -260.0, 40.0 ], "translation": [ -1.0, -2.0, 2.5 ], "scale": [ 1.3, 1.3, 1.1 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ],          "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ],          "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

HAMMER_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 0, -90, 55 ], "translation": [ 0, 7.5, 1.0 ], "scale": [ 1.6, 1.6, 1.6 ] },
    "thirdperson_lefthand":  { "rotation": [ 0, 90, -55 ], "translation": [ 0, 7.5, 1.0 ], "scale": [ 1.6, 1.6, 1.6 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ], "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}

GUN_DISPLAY = {
    "thirdperson_righthand": { "rotation": [ 0.0, -90.0, 45.0 ], "translation": [ 0.0, 1.0, -2.5 ], "scale": [ 0.8, 0.8, 0.8 ] },
    "thirdperson_lefthand":  { "rotation": [ 0.0, 90.0, -45.0 ], "translation": [ 0.0, 1.0, -2.5 ], "scale": [ 0.8, 0.8, 0.8 ] },
    "firstperson_righthand": { "rotation": [ 0, -90, 40 ],        "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] },
    "firstperson_lefthand":  { "rotation": [ 0, 90, -40 ],        "translation": [ 1.13, 4.5, 1.13 ], "scale": [ 1.0, 1.0, 1.0 ] }
}


def resolve_item_display(item_key, orig_name=None, cat=''):
    keys_to_test = []
    if orig_name:
        base_orig = orig_name[:-4] if orig_name.lower().endswith('.png') else orig_name
        keys_to_test.append(base_orig.lower())
    keys_to_test.append(item_key.lower())

    for tk in keys_to_test:
        disp = find_mod_display_for_key(tk)
        if disp:
            return disp

    if is_handheld_item(item_key, cat) or (orig_name and is_handheld_item(orig_name, cat)):
        k = item_key.lower()
        if orig_name:
            k = k + "_" + orig_name.lower()
        if any(x in k for x in ['yoru', 'dai_dao', 'greatsword', 'tram_anh']):
            return GREATSWORD_DISPLAY
        if any(x in k for x in ['bisento', 'murakumogiri', 'thuong', 'giao', 'polearm', 'spear', 'trident', 'dinh_ba', 'dinhba']):
            return POLEARM_DISPLAY
        if any(x in k for x in ['cung', 'bow', 'crossbow', 'xa_thu', 'diet-than-cung']):
            return BOW_DISPLAY
        if any(x in k for x in ['dao', 'knife', 'dagger', 'nanh', 'claw', 'doat_menh', 'nanhfenrir', 'songdao']):
            return DAGGER_DISPLAY
        if any(x in k for x in ['bua', 'hammer', 'axe', 'riu', 'chuy', 'mace', 'dap']):
            return HAMMER_DISPLAY
        if any(x in k for x in ['sung', 'gun', 'flintlock', 'rifle', 'pistol', 'cannon']):
            return GUN_DISPLAY
        return KATANA_DISPLAY

    return None


def get_display_label(item_key, orig_name=None, cat='', kind=''):
    if kind.startswith("3d_"):
        return "🗿 3D Voxel Rig"
    bb = get_mod_blockbench_elements(item_key, orig_name)
    if bb:
        elem_cnt = len(bb.get("elements", []))
        return f"📦 Blockbench 3D ({elem_cnt} elem)"

    k = item_key.lower()
    orig = (orig_name or "").lower()
    full_k = f"{k}_{orig}"

    disp = resolve_item_display(item_key, orig_name, cat)
    if not disp:
        if cat in ('mod_ability', 'icon'):
            return "✨ Skill Icon"
        if cat in ('fruit', 'mod_item') and 'no_mi' in full_k:
            return "🍎 Devil Fruit"
        return "📄 Generated 2D"

    tp = disp.get("thirdperson_righthand", {})
    scale = tp.get("scale", [1.0, 1.0, 1.0])
    s_x = scale[0] if isinstance(scale, list) and len(scale) > 0 else 1.0

    if any(x in full_k for x in ['yoru', 'dai_dao', 'greatsword']):
        return f"⚔️ Greatsword [{s_x}x]"
    if any(x in full_k for x in ['bisento', 'murakumogiri', 'polearm', 'spear', 'trident', 'giao', 'thuong']):
        return f"🔱 Naginata/Polearm [{s_x}x]"
    if any(x in full_k for x in ['cung', 'bow', 'kabuto']):
        return f"🏹 Bow/Slingshot [{s_x}x]"
    if any(x in full_k for x in ['dao', 'knife', 'dagger', 'nanh', 'claw', 'songdao']):
        return f"🔪 Dagger/Knife [{s_x}x]"
    if any(x in full_k for x in ['bua', 'hammer', 'axe', 'riu', 'chuy', 'mace']):
        return f"🔨 Hammer/Axe [{s_x}x]"
    if any(x in full_k for x in ['sung', 'gun', 'flintlock', 'rifle', 'pistol', 'cannon', 'senriku']):
        return f"🔫 Gun/Firearm [{s_x}x]"
    if any(x in full_k for x in ['tonfa', 'hook']):
        return f"🪝 Special Grip [{s_x}x]"
    if any(x in full_k for x in ['pipe', 'staff', 'clima']):
        return f"🦯 Staff/Pipe [{s_x}x]"

    return f"🗡️ Katana/Sword [{s_x}x]"


# ---------------------------------------------------------------------
# 6. MASTER ITEM REGISTRY (PHÂN KHU 1: ID 1 - 99)
# ---------------------------------------------------------------------

PK1_ITEMS = [
    # A. Devil Fruits & Wild Events (1 - 9)
    {"id": 1,  "key": "fruit_mera",     "orig": "mera_mera_no_mi", "sub": "devil_fruits", "kind": "2d",       "cat": "fruit",   "kw": ["mera_mera_no_mi"]},
    {"id": 2,  "key": "fruit_gomu",     "orig": "gomu_gomu_no_mi", "sub": "devil_fruits", "kind": "2d",       "cat": "fruit",   "kw": ["gomu_gomu_no_mi"]},
    {"id": 3,  "key": "fruit_ope",      "orig": "ope_ope_no_mi",  "sub": "devil_fruits", "kind": "2d",       "cat": "fruit",   "kw": ["ope_ope_no_mi"]},
    {"id": 4,  "key": "fruit_seiryu",   "orig": "uo_uo_no_mi",    "sub": "devil_fruits", "kind": "2d",       "cat": "fruit",   "kw": ["uo_uo_no_mi"]},
    {"id": 5,  "key": "df_chest",       "orig": "golden_box",     "sub": "devil_fruits", "kind": "3d_chest", "cat": "chest",   "kw": ["golden_box"]},
    {"id": 6,  "key": "skill_slot_1",   "orig": "hiken",          "sub": "system",       "kind": "2d",       "cat": "icon",    "kw": ["hiken"]},
    {"id": 7,  "key": "skill_slot_2",   "orig": "higan",          "sub": "system",       "kind": "2d",       "cat": "icon",    "kw": ["higan"]},
    {"id": 8,  "key": "skill_slot_3",   "orig": "dai_enkai_entei","sub": "system",       "kind": "2d",       "cat": "icon",    "kw": ["dai_enkai_entei"]},
    {"id": 9,  "key": "skill_awaken",   "orig": "gear_fifth",     "sub": "system",       "kind": "2d",       "cat": "icon",    "kw": ["gear_fifth"]},

    # B. Martial & Haki (20 - 24)
    {"id": 20, "key": "martial_kiem_dao","orig": "yoru",          "sub": "haki_martial", "kind": "2d",       "cat": "weapon",  "kw": ["yoru"]},
    {"id": 21, "key": "martial_luc_thuc","orig": "shigan",        "sub": "haki_martial", "kind": "2d",       "cat": "scroll",  "kw": ["shigan"]},
    {"id": 22, "key": "haki_ken",       "orig": "kenbunshoku_haki_aura","sub": "haki_martial","kind": "2d",  "cat": "icon",    "kw": ["kenbunshoku_haki_aura"]},
    {"id": 23, "key": "haki_buso",      "orig": "busoshoku_haki_hardening","sub": "haki_martial","kind": "2d","cat": "icon",   "kw": ["busoshoku_haki_hardening"]},
    {"id": 24, "key": "haki_hao",       "orig": "haoshoku_haki_infusion","sub": "haki_martial","kind": "2d", "cat": "icon",    "kw": ["haoshoku_haki_infusion"]},

    # C. AoV Items & Chest (30 - 42)
    {"id": 30, "key": "aov_chest",      "orig": "iron_box",       "sub": "aov",          "kind": "2d",       "cat": "chest",   "kw": ["iron_box"]},
    {"id": 31, "key": "thanh_kiem",     "orig": "thanhkiem",      "sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["thanhkiem"]},
    {"id": 32, "key": "muramasa",       "orig": "kiemmuramasa",   "sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["kiemmuramasa"]},
    {"id": 33, "key": "nanh_fenrir",    "orig": "nanhfenrir",     "sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["nanhfenrir"]},
    {"id": 34, "key": "giap_ho_menh",   "orig": "giaphomenh",     "sub": "aov",          "kind": "2d",       "cat": "armor",   "kw": ["giaphomenh"]},
    {"id": 35, "key": "giap_gaia",      "orig": "giapgaia",       "sub": "aov",          "kind": "2d",       "cat": "armor",   "kw": ["giapgaia"]},
    {"id": 36, "key": "huan_chuong_troy","orig": "huanchuongtroy","sub": "aov",          "kind": "2d",       "cat": "icon",    "kw": ["huanchuongtroy"]},
    {"id": 37, "key": "song_dao",       "orig": "songdao",        "sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["songdao"]},
    {"id": 38, "key": "quy_kiem",       "orig": "quykiem",        "sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["quykiem"]},
    {"id": 39, "key": "thuong_xuyen_pha","orig": "diet-than-cung","sub": "aov",          "kind": "2d",       "cat": "weapon",  "kw": ["diet-than-cung", "thuong_xuyen_pha"]},
    {"id": 40, "key": "giap_thong_kho", "orig": "giapthongkho",   "sub": "aov",          "kind": "2d",       "cat": "armor",   "kw": ["giapthongkho"]},
    {"id": 41, "key": "khien_that_truyen","orig": "khienthattruyen","sub": "aov",        "kind": "2d",       "cat": "armor",   "kw": ["khienthattruyen"]},
    {"id": 42, "key": "phu_chu_truong_sinh","orig": "phuchutruongsinh","sub": "aov",     "kind": "2d",       "cat": "icon",    "kw": ["phuchutruongsinh"]},

    # D. Talents, Flags, Bosses, Dummies (50 - 66)
    {"id": 50, "key": "talent_reset",   "orig": "axe_dial",       "sub": "system",       "kind": "2d",       "cat": "icon",    "kw": ["axe_dial"]},
    {"id": 51, "key": "flag_dragon",    "orig": "flag_dragon",    "sub": "crew_flags",   "kind": "3d_flag",  "cat": "flag",    "kw": []},
    {"id": 52, "key": "flag_gold",      "orig": "flag_gold",      "sub": "crew_flags",   "kind": "3d_flag",  "cat": "flag",    "kw": []},
    {"id": 53, "key": "flag_phoenix",   "orig": "flag_phoenix",   "sub": "crew_flags",   "kind": "3d_flag",  "cat": "flag",    "kw": []},
    {"id": 54, "key": "boss_akainu_icon","orig": "magu_magu_no_mi","sub": "bosses",     "kind": "2d",       "cat": "icon",    "kw": ["magu_magu_no_mi"]},
    {"id": 55, "key": "boss_aokiji_icon","orig": "hie_hie_no_mi", "sub": "bosses",      "kind": "2d",       "cat": "icon",    "kw": ["hie_hie_no_mi"]},
    {"id": 56, "key": "bounty_poster",  "orig": "wanted_poster",  "sub": "system",       "kind": "2d",       "cat": "poster",  "kw": ["wanted_poster"]},
    {"id": 60, "key": "seaking_idle",   "orig": "seaking_idle",   "sub": "bosses",       "kind": "3d_seaking_idle", "cat": "boss", "kw": []},
    {"id": 61, "key": "seaking_attack", "orig": "seaking_attack", "sub": "bosses",       "kind": "3d_seaking_attack", "cat": "boss", "kw": []},
    {"id": 62, "key": "seaking_roar",   "orig": "seaking_roar",   "sub": "bosses",       "kind": "3d_seaking_roar", "cat": "boss", "kw": []},
    {"id": 63, "key": "dummy_normal_idle","orig": "dummy_normal_idle","sub": "dummies",   "kind": "3d_dummy_normal", "cat": "dummy", "kw": []},
    {"id": 64, "key": "dummy_boss_idle","orig": "dummy_boss_idle","sub": "dummies",     "kind": "3d_dummy_boss", "cat": "dummy", "kw": []},
    {"id": 65, "key": "dummy_logia_idle","orig": "dummy_logia_idle","sub": "dummies",   "kind": "3d_dummy_logia", "cat": "dummy", "kw": []},
    {"id": 66, "key": "dummy_hit_shake","orig": "dummy_hit_shake","sub": "dummies",     "kind": "3d_dummy_shake", "cat": "dummy", "kw": []},
]


def slugify_vn(text):
    text = unicodedata.normalize('NFD', text)
    text = ''.join(c for c in text if unicodedata.category(c) != 'Mn')
    text = text.lower().replace('đ', 'd').replace('Đ', 'd')
    text = re.sub(r'[^a-z0-9_-]', '_', text)
    text = re.sub(r'_+', '_', text).strip('_')
    return text


def collect_pk2_items(pk1_orig_keys):
    """
    Scans Mine Mine no Mi items/abilities and trangbilq.
    Filters out anything in pk1_orig_keys.
    Sorts alphabetically (A-Z) and assigns IDs starting at 100.
    """
    mod_root = os.path.join(MOD_SOURCE_DIR, "mine-mine-no-mi-1.21.1-0.11.0.jar.src")
    items_dir = os.path.join(mod_root, "assets", "mineminenomi", "textures", "items")
    abilities_dir = os.path.join(mod_root, "assets", "mineminenomi", "textures", "abilities")
    lq_dir = os.path.join(MOD_SOURCE_DIR, "trangbilq")

    pk2_entries = []
    seen_keys = set()

    # 1. Mod Items
    if os.path.exists(items_dir):
        for r, ds, fs in os.walk(items_dir):
            for f in fs:
                if f.lower().endswith(".png"):
                    base = f[:-4].lower()
                    if base in pk1_orig_keys:
                        continue
                    rel_sub = os.path.relpath(r, items_dir).replace("\\", "/")
                    k = base
                    if k in seen_keys:
                        k = f"{base}_item"
                    seen_keys.add(k)
                    tex = f"items/mineminenomi/items/{rel_sub}/{base}" if rel_sub != "." else f"items/mineminenomi/items/{base}"
                    model = f"item/mineminenomi/items/{rel_sub}/{base}" if rel_sub != "." else f"item/mineminenomi/items/{base}"
                    pk2_entries.append({
                        "key": k,
                        "orig": f,
                        "cat": "mod_item",
                        "src": os.path.join(r, f),
                        "tex": tex,
                        "model": model
                    })

    # 2. Mod Abilities
    if os.path.exists(abilities_dir):
        for r, ds, fs in os.walk(abilities_dir):
            for f in fs:
                if f.lower().endswith(".png"):
                    base = f[:-4].lower()
                    if base in pk1_orig_keys:
                        continue
                    k = base
                    if k in seen_keys:
                        k = f"{base}_ability"
                    seen_keys.add(k)
                    tex = f"items/mineminenomi/abilities/{base}"
                    model = f"item/mineminenomi/abilities/{base}"
                    pk2_entries.append({
                        "key": k,
                        "orig": f,
                        "cat": "mod_ability",
                        "src": os.path.join(r, f),
                        "tex": tex,
                        "model": model
                    })

    # 3. Extra AoV Items
    if os.path.exists(lq_dir):
        for f in os.listdir(lq_dir):
            if f.lower().endswith(".png"):
                slug = slugify_vn(f[:-4])
                if slug in pk1_orig_keys or f[:-4].lower() in pk1_orig_keys:
                    continue
                k = slug
                if k in seen_keys:
                    k = f"{slug}_lq"
                seen_keys.add(k)
                tex = f"items/trangbilq/{slug}"
                model = f"item/trangbilq/{slug}"
                pk2_entries.append({
                    "key": k,
                    "orig": f,
                    "cat": "aov_item",
                    "src": os.path.join(lq_dir, f),
                    "tex": tex,
                    "model": model
                })

    pk2_entries.sort(key=lambda x: x["key"])

    for idx, e in enumerate(pk2_entries):
        e["id"] = 100 + idx

    return pk2_entries


# ---------------------------------------------------------------------
# 7. ASSET SCANNER & MOD MATCHING
# ---------------------------------------------------------------------

def scan_mod_assets():
    png_index = {}
    for sdir in [MOD_SOURCE_DIR]:
        if not os.path.exists(sdir):
            continue
        for root, dirs, files in os.walk(sdir):
            for file in files:
                lower_f = file.lower()
                if lower_f.endswith(".png"):
                    base_name = lower_f[:-4]
                    if base_name not in png_index:
                        png_index[base_name] = os.path.join(root, file)
    print(f"[Scanner] Indexed {len(png_index)} mod PNG textures.")
    return png_index


def match_mod_texture(item, png_index):
    for kw in item.get("kw", []):
        lkw = kw.lower()
        if lkw in png_index:
            return png_index[lkw]
        for base_name, full_path in png_index.items():
            if lkw in base_name:
                return full_path
    return None


# ---------------------------------------------------------------------
# 7.5 FAITHFUL 64x SMOOTH VIETNAMESE BITMAP FONT GENERATOR
# ---------------------------------------------------------------------

MC_CP437_ASCII_TABLE = (
    "ÀÁÂÈÊËÍÓÔÕÚßãõğİ"
    "ıŒœŞşŴŵžȇ\x00\x00\x00\x00\x00\x00\x00"
    " !\"#$%&'()*+,-./"
    "0123456789:;<=>?"
    "@ABCDEFGHIJKLMNO"
    "PQRSTUVWXYZ[\\]^_"
    "`abcdefghijklmno"
    "pqrstuvwxyz{|}~\x00"
    "ÇüéâäàåçêëèïîìÄÅ"
    "ÉæÆôöòûùÿÖÜø£Ø×ƒ"
    "áíóúñÑªº¿®¬½¼¡«»"
    "░▒▓│┤╡╢╖╕╣║╗╝╜╛┐"
    "└┴┬├─┼╞╟╚╔╩╦╠═╬╧"
    "╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀"
    "αβΓπΣσμτΦΘΩδ∞∅∈∩"
    "≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\x00"
)


def find_system_font(font_names):
    windir = os.environ.get("WINDIR", "C:\\Windows")
    fonts_dir = os.path.join(windir, "Fonts")
    for name in font_names:
        full_path = os.path.join(fonts_dir, name)
        if os.path.exists(full_path):
            return full_path
    return None


def generate_vietnamese_bitmap_fonts(rp_dir):
    """
    Generates high-resolution Faithful 64x Smooth Bitmap Fonts supporting 100% Vietnamese
    and Unicode BMP characters for Minecraft 1.12.2.
    Produces:
      - assets/minecraft/textures/font/ascii.png (1024x1024, 16x16 grid of 64x64 CP437 cells)
      - assets/minecraft/textures/font/unicode_page_00.png (U+0000 - U+00FF, Latin-1 vowels)
      - assets/minecraft/textures/font/unicode_page_01.png (U+0100 - U+01FF, Đ, đ, Ă, ă, Ơ, ơ, Ư, ư...)
      - assets/minecraft/textures/font/unicode_page_1e.png (U+1E00 - U+1EFF, 90 Vietnamese compound accented letters)
      - assets/minecraft/textures/font/unicode_page_20.png .. 27.png (U+2000 - U+27FF, RPG BMP symbols)
      - assets/minecraft/font/glyph_sizes.bin (65,536 bytes character width table)
    """
    print("\n--- GENERATING FAITHFUL 64x SMOOTH VIETNAMESE BITMAP FONTS ---")

    font_tex_dir = os.path.join(rp_dir, "assets", "minecraft", "textures", "font")
    font_meta_dir = os.path.join(rp_dir, "assets", "minecraft", "font")
    os.makedirs(font_tex_dir, exist_ok=True)
    os.makedirs(font_meta_dir, exist_ok=True)

    primary_candidates = ["arialbd.ttf", "tahomabd.ttf", "segoeuib.ttf", "arial.ttf"]
    symbol_candidates = ["seguisym.ttf", "seguiemj.ttf", "segoeuib.ttf", "arialbd.ttf"]

    primary_font_path = find_system_font(primary_candidates)
    symbol_font_path = find_system_font(symbol_candidates)

    if not primary_font_path:
        raise FileNotFoundError("Could not find suitable bold TrueType font for Vietnamese glyph rendering!")
    if not symbol_font_path:
        symbol_font_path = primary_font_path

    print(f"[Font Generator] Primary Font: {primary_font_path}")
    print(f"[Font Generator] Symbol Font:  {symbol_font_path}")

    font_size = 42
    baseline_y = 48
    cell_size = 64
    sheet_size = 1024

    font_main = ImageFont.truetype(primary_font_path, font_size)
    font_sym = ImageFont.truetype(symbol_font_path, font_size)

    tofu_mask = font_main.getmask("\uFFFF")
    tofu_size = tofu_mask.size
    tofu_bytes = bytes(tofu_mask)

    def is_tofu(f, ch):
        try:
            m = f.getmask(ch)
            return m.size == tofu_size and bytes(m) == tofu_bytes
        except Exception:
            return True

    def pick_font(cp, ch):
        if 0x2000 <= cp <= 0x2BFF or 0x0370 <= cp <= 0x03FF:
            return font_sym
        if is_tofu(font_main, ch):
            return font_sym
        return font_main

    # 1. Generate ascii.png (CP437, 1024x1024)
    print("[Font Generator] Rendering ascii.png (CP437 table, 1024x1024)...")
    ascii_img = Image.new("RGBA", (sheet_size, sheet_size), (0, 0, 0, 0))

    for idx, ch in enumerate(MC_CP437_ASCII_TABLE):
        if ch == "\x00" or ch == " " or idx == 32:
            continue
        row = idx // 16
        col = idx % 16
        cp = ord(ch)
        f = pick_font(cp, ch)

        temp_cell = Image.new("RGBA", (cell_size, cell_size), (0, 0, 0, 0))
        d_temp = ImageDraw.Draw(temp_cell)
        d_temp.text((4, baseline_y), ch, font=f, fill=(255, 255, 255, 255), anchor="ls")
        bbox = temp_cell.getbbox()
        if bbox:
            shift_x = 2 - bbox[0]
            final_cell = Image.new("RGBA", (cell_size, cell_size), (0, 0, 0, 0))
            d_final = ImageDraw.Draw(final_cell)
            d_final.text((4 + shift_x, baseline_y), ch, font=f, fill=(255, 255, 255, 255), anchor="ls")
            ascii_img.paste(final_cell, (col * cell_size, row * cell_size))

    ascii_out = os.path.join(font_tex_dir, "ascii.png")
    ascii_img.save(ascii_out, "PNG", optimize=True)
    print(f"[Font Generator] Saved ascii.png ({os.path.getsize(ascii_out):,} bytes)")

    # 2. Load or initialize glyph_sizes.bin (65,536 bytes)
    glyph_sizes_bin_path = os.path.join(font_meta_dir, "glyph_sizes.bin")
    if os.path.exists(glyph_sizes_bin_path) and os.path.getsize(glyph_sizes_bin_path) == 65536:
        with open(glyph_sizes_bin_path, "rb") as gf:
            glyph_sizes = bytearray(gf.read())
        print(f"[Font Generator] Loaded existing glyph_sizes.bin (65,536 bytes)")
    else:
        glyph_sizes = bytearray(65536)
        print(f"[Font Generator] Initialized new glyph_sizes.bin array (65,536 bytes)")

    # 3. Generate Unicode Pages
    unicode_pages = [0x00, 0x01, 0x1E, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27]
    total_glyphs_updated = 0

    for page in unicode_pages:
        page_img = Image.new("RGBA", (sheet_size, sheet_size), (0, 0, 0, 0))
        page_updated = 0

        for offset in range(256):
            cp = (page << 8) | offset
            # Skip control characters and unrenderable whitespace
            if cp < 32 or (0x7F <= cp <= 0x9F) or cp == 0x20 or cp == 0xA0:
                continue

            ch = chr(cp)
            f = pick_font(cp, ch)
            if is_tofu(f, ch):
                continue

            cell = Image.new("RGBA", (cell_size, cell_size), (0, 0, 0, 0))
            draw = ImageDraw.Draw(cell)
            draw.text((4, baseline_y), ch, font=f, fill=(255, 255, 255, 255), anchor="ls")
            bb = cell.getbbox()

            if bb:
                min_x, min_y, max_x, max_y = bb
                row = offset // 16
                col = offset % 16
                page_img.paste(cell, (col * cell_size, row * cell_size))

                # Minecraft 1.12.2 coordinate conversion: 16 units across cell_size (64px) -> 4px per unit
                start_col = max(0, min(15, min_x // 4))
                end_col = max(0, min(15, (max_x - 1) // 4))
                glyph_sizes[cp] = (start_col << 4) | end_col
                page_updated += 1

        page_filename = f"unicode_page_{page:02x}.png"
        page_path = os.path.join(font_tex_dir, page_filename)
        page_img.save(page_path, "PNG", optimize=True)
        total_glyphs_updated += page_updated
        print(f"[Font Generator] Saved {page_filename} ({page_updated} glyphs, {os.path.getsize(page_path):,} bytes)")

    # 4. Save updated glyph_sizes.bin
    with open(glyph_sizes_bin_path, "wb") as gf:
        gf.write(glyph_sizes)
    print(f"[Font Generator] Saved updated glyph_sizes.bin ({len(glyph_sizes):,} bytes, {total_glyphs_updated} glyphs synced)")
    print("[Font Generator] Bitmap font pipeline completed successfully!")


# ---------------------------------------------------------------------
# 8. RESOURCE PACK AUDIT & 48 VALIDATION GATES
# ---------------------------------------------------------------------

def validate_resource_pack(all_items):
    print("\n--- RUNNING RESOURCE PACK AUDIT (VALIDATE 1.12.2) ---")
    errors = []
    checks_passed = 0

    # Gate 1: pack.mcmeta
    mcmeta_path = os.path.join(RP_DIR, "pack.mcmeta")
    if not os.path.exists(mcmeta_path):
        errors.append("pack.mcmeta is missing!")
    else:
        with open(mcmeta_path, "r", encoding="utf-8") as f:
            meta = json.load(f)
            if meta.get("pack", {}).get("pack_format") != 3:
                errors.append(f"pack_format is {meta.get('pack', {}).get('pack_format')}, expected 3!")
            else:
                checks_passed += 1

    # Gate 2: diamond_hoe.json
    hoe_path = os.path.join(MODELS_ITEM_DIR, "diamond_hoe.json")
    if not os.path.exists(hoe_path):
        errors.append("diamond_hoe.json is missing!")
    else:
        with open(hoe_path, "r", encoding="utf-8") as f:
            hoe_data = json.load(f)
        overrides = hoe_data.get("overrides", [])
        if len(overrides) != len(all_items):
            errors.append(f"diamond_hoe.json has {len(overrides)} overrides, expected {len(all_items)}!")
        else:
            checks_passed += 1

        prev_dmg = -1.0
        for idx, o in enumerate(overrides):
            pred = o.get("predicate", {})
            if "damaged" in pred:
                errors.append(f"Override index {idx} contains 'damaged' key! Must be omitted in 1.12.2.")
            dmg = pred.get("damage")
            if dmg is None:
                errors.append(f"Override index {idx} missing 'damage' value!")
            elif dmg <= prev_dmg:
                errors.append(f"Override index {idx} damage ({dmg}) is not strictly greater than previous ({prev_dmg})! Must be sorted ascending.")
            prev_dmg = dmg

            model_rel = o.get("model", "")
            expected_model_file = os.path.join(ASSETS_DIR, "models", model_rel.replace("/", os.sep) + ".json")
            if not os.path.exists(expected_model_file):
                errors.append(f"Override model target does not exist on disk: {expected_model_file}")
            else:
                with open(expected_model_file, "r", encoding="utf-8") as mf:
                    m_data = json.load(mf)
                for slot, tex_logical in m_data.get("textures", {}).items():
                    if tex_logical.startswith("items/"):
                        t_sub = strip_prefix(tex_logical, "items/").replace("/", os.sep) + ".png"
                        t_path = os.path.join(TEXTURES_DIR, "items", t_sub)
                        if not os.path.exists(t_path):
                            errors.append(f"Model {o['model']} texture {slot} '{tex_logical}' not found at {t_path}!")
                    elif tex_logical.startswith("mineminenomi:"):
                        m_sub = tex_logical[len("mineminenomi:"):].replace("/", os.sep) + ".png"
                        m_path = os.path.join(RP_DIR, "assets", "mineminenomi", "textures", m_sub)
                        if not os.path.exists(m_path):
                            errors.append(f"Model {o['model']} mod texture {slot} '{tex_logical}' not found at {m_path}!")

        checks_passed += 1

    # Gate 3: Check 3D Models & 100% Solid Textures
    for item in all_items:
        kind = item.get("kind", "2d")
        if not kind.startswith("3d_"):
            continue

        key = item["key"]
        sub_dir = item.get("sub", "")
        model_file = os.path.join(MODELS_CUSTOM_DIR, sub_dir, f"{key}.json")
        tex_file = os.path.join(TEXTURES_CUSTOM_DIR, sub_dir, f"{key}.png")

        if not os.path.exists(model_file):
            errors.append(f"3D Model file missing: {model_file}")
            continue
        if not os.path.exists(tex_file):
            errors.append(f"3D Texture PNG missing: {tex_file}")
            continue

        dec = decode_png(tex_file)
        if not dec:
            errors.append(f"3D Texture {key}.png failed to decode!")
        else:
            _, _, t_pixels = dec
            non_solid = [p for p in t_pixels if p[3] != 255]
            if non_solid:
                errors.append(f"3D Texture {key}.png has {len(non_solid)} non-solid pixels! Must be 100% alpha = 255.")
            else:
                checks_passed += 1

        with open(model_file, "r", encoding="utf-8") as f:
            m_data = json.load(f)

        for el in m_data.get("elements", []):
            f_arr, t_arr = el.get("from", []), el.get("to", [])
            for axis_idx in range(3):
                if f_arr[axis_idx] >= t_arr[axis_idx]:
                    errors.append(f"3D Model {key}.json element '{el.get('name')}' inverted: from[{axis_idx}] ({f_arr[axis_idx]}) >= to[{axis_idx}] ({t_arr[axis_idx]})!")

    # Gate 4: Faithful 64x Smooth Vietnamese Bitmap Font & Glyph Sizes (Optional)
    if ENABLE_CUSTOM_FONT:
        font_tex_dir = os.path.join(RP_DIR, "assets", "minecraft", "textures", "font")
        font_meta_dir = os.path.join(RP_DIR, "assets", "minecraft", "font")

        # Check ascii.png
        ascii_path = os.path.join(font_tex_dir, "ascii.png")
        if not os.path.exists(ascii_path):
            errors.append("ascii.png is missing from textures/font/!")
        else:
            with Image.open(ascii_path) as im:
                if im.size != (1024, 1024):
                    errors.append(f"ascii.png size is {im.size}, expected (1024, 1024) for Faithful 64x!")
                else:
                    checks_passed += 1

        # Check unicode pages: 00, 01, 1e, 20..27
        required_pages = [0x00, 0x01, 0x1E, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27]
        for page in required_pages:
            p_name = f"unicode_page_{page:02x}.png"
            p_path = os.path.join(font_tex_dir, p_name)
            if not os.path.exists(p_path):
                errors.append(f"{p_name} is missing from textures/font/!")
            else:
                with Image.open(p_path) as im:
                    if im.size != (1024, 1024):
                        errors.append(f"{p_name} size is {im.size}, expected (1024, 1024)!")
                    else:
                        checks_passed += 1

        # Check glyph_sizes.bin
        gs_bin_path = os.path.join(font_meta_dir, "glyph_sizes.bin")
        if not os.path.exists(gs_bin_path):
            errors.append("glyph_sizes.bin is missing from assets/minecraft/font/!")
        elif os.path.getsize(gs_bin_path) != 65536:
            errors.append(f"glyph_sizes.bin size is {os.path.getsize(gs_bin_path)}, expected 65,536 bytes!")
        else:
            with open(gs_bin_path, "rb") as gf:
                gs_data = gf.read()
            test_vietnamese = [0x1EA0, 0x1EAF, 0x0110, 0x00C1, 0x01A1, 0x01AF]
            all_vn_valid = True
            for vn_cp in test_vietnamese:
                val = gs_data[vn_cp]
                s_col = val >> 4
                e_col = val & 15
                if val == 0 or s_col > e_col:
                    errors.append(f"glyph_sizes.bin entry for U+{vn_cp:04X} invalid ({hex(val)})!")
                    all_vn_valid = False
            if all_vn_valid:
                checks_passed += 1

    if errors:
        print(f"\n[FAILED] Resource pack audit failed with {len(errors)} errors:")
        for err in errors[:25]:
            print(f"  - {err}")
        return False

    print(f"[PASSED] Audit 100% SUCCESSFUL! Passed {checks_passed} validation gates with ZERO errors.")
    return True


# ---------------------------------------------------------------------
# 9. DOCUMENTATION & SKRIPT REGISTRY GENERATION
# ---------------------------------------------------------------------

def generate_documentation_and_registry(pk1_items, pk2_items):
    """
    Generates RESOURCEPACK_ASSET_MAPPING.md and plugins/Skript/rp_models.txt
    """
    print("\n[Docs & Registry] Generating RESOURCEPACK_ASSET_MAPPING.md and rp_models.txt...")

    # A. rp_models.txt
    os.makedirs(SKRIPT_DIR, exist_ok=True)
    with open(RP_MODELS_TXT, "w", encoding="utf-8") as f:
        # PK1 entries
        for item in pk1_items:
            f.write(f"{item['key']}:{item['id']}\n")
            if item.get("orig") and item["orig"] != item["key"]:
                f.write(f"{item['orig']}:{item['id']}\n")
            for kw in item.get("kw", []):
                if kw != item["key"] and kw != item.get("orig"):
                    f.write(f"{kw}:{item['id']}\n")
        # PK2 entries
        for item in pk2_items:
            f.write(f"{item['key']}:{item['id']}\n")
            if item.get("orig"):
                orig_base = item["orig"][:-4].lower() if item["orig"].endswith(".png") else item["orig"].lower()
                if orig_base != item["key"]:
                    f.write(f"{orig_base}:{item['id']}\n")

    print(f"[OK] Generated {RP_MODELS_TXT} with {len(pk1_items) + len(pk2_items)} items mapped.")

    # B. RESOURCEPACK_ASSET_MAPPING.md
    with open(MAPPING_MD, "w", encoding="utf-8") as f:
        f.write("# SỔ TAY ÁNH XẠ TOÀN DIỆN RESOURCE PACK (ONEPIECE SKYBLOCK RPG)\n\n")
        f.write("> **Phiên bản:** MC 1.12.2 (Pack Format 3) — Base Layer: **Classic Faithful 64x64**\n")
        f.write("> **Tổng số vật phẩm:** %d vật phẩm (Phân khu 1: %d, Phân khu 2: %d)\n" % (len(pk1_items) + len(pk2_items), len(pk1_items), len(pk2_items)))
        f.write("> **Công thức Predicate Durability:** `damage = math.floor(((id - 0.05) / 1561.0) * 10000000) / 10000000`\n\n")

        f.write("## MỤC 1: KIẾN TRÚC RESOURCE PACK HỢP NHẤT\n")
        f.write("* **Base Layer Faithful 64x64:** Toàn bộ khối, cây cối, giao diện vanilla được nâng cấp lên 64x64 HD sắc nét.\n")
        f.write("* **Độ phân giải 2D:** 100% item và icon mod / AoV được chuẩn hóa về **64x64 RGBA (8-bit)**.\n")
        f.write("* **Mô hình 3D Rig:** 100% sử dụng Texture Atlas đặc (`alpha = 255`), triệt tiêu hoàn toàn lỗi thủng mặt và Z-fighting.\n")
        f.write("* **Chuyển giao Toàn bộ 600 Model Mod Mine Mine no Mi:** Toàn bộ model khối, item, Blockbench 3D (bazooka, axe hand, dial, den den mushi...) được chuyển giao vào `assets/mineminenomi/models/`.\n")
        f.write("* **Cấu hình Cầm Tay Chuẩn Mod (Display Transformations):** Toàn bộ vũ khí, kiếm katana, đại đao, đinh ba, súng, búa, cung tên... được kế thừa chính xác 100% ma trận góc xoay, tỉ lệ và độ dài tay cầm chuẩn anime từ mod Mine Mine no Mi.\n")
        f.write("* **Cơ chế Nạp Skript:** Toàn bộ ID được nạp qua bảng biến `{rp_model_id::*}` từ `plugins/Skript/rp_models.txt` khi server khởi động.\n\n")

        f.write("## MỤC 2: BẢNG ÁNH XẠ PHÂN KHU 1 (ID 1 - 99: VẬT PHẨM & 3D RIG ACTIVE)\n\n")
        f.write("| Damage ID | Float Predicate | Tên File .PNG Gốc | Kiểu Cầm / Rig | Đường Dẫn Model JSON | Key Skript & Alias | Module .SK Sử Dụng |\n")
        f.write("|:---:|:---:|:---|:---|:---|:---|:---|\n")

        module_map = {
            "devil_fruits": "07_devil_fruits.sk",
            "haki_martial": "08_haki_and_bounty.sk",
            "aov": "02_aov_equipment.sk",
            "system": "00_visual_art_lib.sk / 01_talent_core.sk",
            "crew_flags": "11_pirate_crew_koth.sk",
            "bosses": "09_onepiece_events.sk",
            "dummies": "10_combat_dummy.sk"
        }

        for item in pk1_items:
            i_id = item["id"]
            dmg = math.floor(((i_id - 0.05) / 1561.0) * 10000000) / 10000000
            orig_png = item.get("orig", item["key"]) + (".png" if not item.get("orig", "").endswith(".png") else "")
            model_path = f"item/custom/{item['sub']}/{item['key']}"
            aliases = ", ".join([item["key"]] + [k for k in item.get("kw", []) if k != item["key"]][:3])
            mod_sk = module_map.get(item["sub"], "00_visual_art_lib.sk")
            disp_label = get_display_label(item["key"], item.get("orig"), item.get("cat", ""), item.get("kind", ""))
            f.write(f"| **{i_id}** | `{dmg:.7f}` | `{orig_png}` | {disp_label} | `{model_path}` | `{aliases}` | `{mod_sk}` |\n")

        f.write("\n## MỤC 3: BẢNG ÁNH XẠ PHÂN KHU 2 (ID 100 - 1550: KHO TÀI NGUYÊN MỞ RỘNG MOD & AOV)\n\n")
        f.write("| Damage ID | Float Predicate | Tên File .PNG Gốc | Phân Loại | Kiểu Cầm / Display | Đường Dẫn Model JSON | Key Gọi Trong Skript |\n")
        f.write("|:---:|:---:|:---|:---:|:---|:---|:---|\n")

        for item in pk2_items:
            i_id = item["id"]
            dmg = math.floor(((i_id - 0.05) / 1561.0) * 10000000) / 10000000
            orig_png = item["orig"]
            cat_label = "Mod Item" if item["cat"] == "mod_item" else ("Mod Ability" if item["cat"] == "mod_ability" else "AoV Item")
            disp_label = get_display_label(item["key"], item.get("orig"), item.get("cat", ""))
            f.write(f"| {i_id} | `{dmg:.7f}` | `{orig_png}` | {cat_label} | {disp_label} | `{item['model']}` | `{item['key']}` |\n")

        f.write("\n## MỤC 4: BẢNG THÔNG SỐ MA TRẬN DISPLAY / CÁCH CẦM VŨ KHÍ CHUẨN MOD\n\n")
        f.write("| Nhóm Vũ Khí | Tỉ Lệ Phóng To (Scale) | Góc Cầm (Third-Person) | Tọa Độ Cầm (Translation) | Góc Nhìn Thứ Nhất (First-Person) | Vũ Khí Đại Diện |\n")
        f.write("|:---|:---:|:---:|:---:|:---:|:---|\n")
        f.write("| **⚔️ Greatsword (Đại Kiếm)** | `[2.5, 2.5, 1.0]` | `rot: [15, -90, 55]` | `trans: [0, 18.0, 2.5]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Hắc Kiếm Yoru, Đại đao trảm anh |\n")
        f.write("| **🔱 Naginata / Polearm (Đại Đao/Thương)** | `[3.6, 3.6, 3.6]` | `rot: [0, -90, 5]` | `trans: [0, 7.5, 8.0]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Bisento, Murakumogiri, Nonosama Trident, Thương Longinus |\n")
        f.write("| **🗡️ Katana Anime (Kiếm Nhật)** | `[2.0, 2.0, 1.0]` | `rot: [0, -90, 55]` | `trans: [0, 11.5, -1.0]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Enma, Shusui, Wado Ichimonji, Kikoku, Thanh kiếm, Muramasa, Quỷ kiếm |\n")
        f.write("| **🔪 Dagger / Knife (Dao Găm/Vuốt)** | `[1.6, 1.6, 1.0]` | `rot: [0, -90, 55]` | `trans: [0, 8.0, -0.5]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Bandit Knife, Mihawk Knife, Nanh Fenrir, Song đao |\n")
        f.write("| **🏹 Bow / Slingshot (Cung/Ná Bắn)** | `[1.3, 1.3, 1.1]` | `rot: [-80, 260, -40]` | `trans: [-1.0, -2.0, 2.5]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Kuja Bow, Kabuto, Kuro Kabuto, Diệt thần cung |\n")
        f.write("| **🔨 Heavy Hammer / Axe (Búa Tạ/Rìu)** | `[1.6, 1.6, 1.6]` | `rot: [0, -90, 55]` | `trans: [0, 7.5, 1.0]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | 100t Hammer, 10t Hammer, 5t Hammer, Rìu chiến |\n")
        f.write("| **🔫 Firearm / Gun (Súng Bắn)** | `[0.8, 0.8, 0.8]` | `rot: [0, -90, 45]` | `trans: [0.0, 1.0, -2.5]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Flintlock, Walker, Senriku, Súng hải tặc |\n")
        f.write("| **🪝 Special Grip (Vũ Khí Ngược/Móc)** | `[1.1, 1.1, 1.5]` | `rot: [0, -90, -145]` | `trans: [0.0, -5.5, 6.5]` | `rot: [0, 90, -90]` | Tonfa (cầm ngược cẳng tay), Móc câu Crocodile (Hook) |\n")
        f.write("| **🦯 Staff / Pipe (Gậy/Ống Nước)** | `[2.4, 2.4, 2.0]` | `rot: [0, -90, 5]` | `trans: [0, 6.5, 8.0]` | `rot: [0, -90, 40], scale: [1, 1, 1]` | Pipe (Ống nước Sabo), Clima Tact, Sorcery Clima Tact |\n")

        f.write("\n## MỤC 5: KHO MÔ HÌNH 3D BLOCKBENCH & TOÀN BỘ 600 MODEL MOD (`assets/mineminenomi/models/`)\n\n")
        f.write("### 1. Mô hình 3D Blockbench đặc biệt trong `models/item/`:\n")
        f.write("* `item/bazooka.json`: Khẩu pháo vác vai Bazooka 3D chuẩn xác (18 elements), hiển thị vác trên vai người chơi.\n")
        f.write("* `item/axe_hand.json`: Cánh tay rìu Morgan 3D gắn trực tiếp vào tay người chơi.\n")
        f.write("* `item/cat_claws.json`: Bộ móng vuốt mèo của thuyền trưởng Kuro 3D (11 elements).\n")
        f.write("* `item/golden_den_den_mushi.json`: Ốc sên truyền tin vàng Buster Call 3D.\n")
        f.write("* `item/cigar.json`, `item/cigarette.json`, `item/three_cigars.json`: Điếu xì gà của Smoker 3D.\n")
        f.write("* `item/smoking_pipe.json`: Tẩu thuốc 3D.\n\n")

        f.write("### 2. Mô hình 3D Khối trong `models/block/`:\n")
        f.write("* **Hệ thống Dial đảo Trời:** `axe_dial`, `breath_dial`, `eisen_dial`, `flame_dial`, `flash_dial`, `impact_dial`, `milky_dial`, `reject_dial` (đầy đủ ma trận xoay góc Blockbench).\n")
        f.write("* **Hệ thống Ốc Sên Den Den Mushi:** `den_den_mushi1`, `den_den_mushi2`, `den_den_mushi3` (mô hình ốc sên 3D nguyên bản).\n")
        f.write("* **Hệ thống Bia Đá Cổ Đại Poneglyph:** `poneglyph_1`, `poneglyph_2`, `poneglyph_3`, `road_poneglyph`.\n")
        f.write("* **Khối & Chấn song Hải Lâu Thạch:** `kairoseki_block`, `kairoseki_ore`, `kairoseki_bars_post`, `kairoseki_bars_side`, `kairoseki_bars_cap`.\n\n")

        f.write("## MỤC 6: HƯỚNG DẪN GỌI VẬT PHẨM TRONG SKRIPT & LỆNH TEST\n\n")
        f.write("### 1. Gọi trong mã nguồn Skript:\n")
        f.write("```skript\n")
        f.write('# Gọi bằng tên file .png gốc hoặc alias:\n')
        f.write('set {_mera} to makeCustomRPItem("mera_mera_no_mi", "&6&l✦ TRÁI MERA MERA ✦", ("&7Trái Ác Quỷ Hệ Logia"))\n')
        f.write('set {_yoru} to makeCustomRPItem("yoru", "&2&l✦ HẮC KIẾM YORU ✦", ("&7Cực phẩm bảo kiếm"))\n')
        f.write('set {_pika} to makeCustomRPItem("pika_pika_no_mi", "&e&l✦ TRÁI PIKA PIKA ✦", ("&7Vật phẩm mở rộng từ Phân khu 2"))\n')
        f.write('set {_bazooka} to makeCustomRPItem("bazooka", "&c&l✦ PHÁO BAZOOKA 3D ✦", ("&7Vũ khí hỏa lực hạng nặng"))\n')
        f.write("```\n\n")

        f.write("### 2. Lệnh kiểm thử trực tiếp trong game (Admin / OP):\n")
        f.write("* Lấy vật phẩm bất kỳ bằng tên: `/visualtest get <tên_file_png_không_đuôi> [<người_chơi>]`\n")
        f.write("  * Ví dụ kiếm chuẩn mod: `/visualtest get yoru` (Đại kiếm khổng lồ 2.5x)\n")
        f.write("  * Ví dụ đại đao chuẩn mod: `/visualtest get bisento` (Đại đao Râu Trắng 3.6x)\n")
        f.write("  * Ví dụ pháo 3D chuẩn mod: `/visualtest get bazooka` (Mô hình Blockbench 3D 18 elements)\n")
        f.write("  * Ví dụ Katana chuẩn mod: `/visualtest get enma` (Katana anime 2.0x)\n")
        f.write("  * Ví dụ kiếm Liên Quân: `/visualtest get thanhkiem` (Áp dụng ma trận Katana anime)\n")
        f.write("  * Ví dụ cung tên Liên Quân: `/visualtest get diet-than-cung` (Áp dụng ma trận Kuja Bow)\n")
        f.write("  * Ví dụ dao găm Liên Quân: `/visualtest get nanhfenrir` (Áp dụng ma trận Dagger)\n")
        f.write("  * Ví dụ trái ác quỷ: `/visualtest get pika_pika_no_mi`\n")
        f.write("* Triệu hồi mô hình 3D Vua Biển: `/visualtest 3d`\n")
        f.write("* Xóa toàn bộ mô hình 3D xung quanh: `/visualtest clear`\n")

    print(f"[OK] Generated {MAPPING_MD}.")


# ---------------------------------------------------------------------
# 10. MAIN PIPELINE EXECUTION
# ---------------------------------------------------------------------

def main():
    print("=== OnePiece Skyblock Unified Resource Pack Pipeline (MC 1.12.2) ===")

    # 1. Base Layer Faithful 64x64
    faithful_zip = os.path.join(MOD_SOURCE_DIR, "Classic faithful64x-1.0r 1.12.2.zip")
    if os.path.exists(faithful_zip):
        print(f"[Faithful 64x] Extracting base layer from {faithful_zip}...")
        with zipfile.ZipFile(faithful_zip, "r") as zf:
            zf.extractall(RP_DIR)
        print("[Faithful 64x] Base layer extracted successfully.")
    else:
        print(f"[Warning] Faithful 64x archive not found at {faithful_zip}!")

    # 2. Configure pack.mcmeta
    mcmeta_path = os.path.join(RP_DIR, "pack.mcmeta")
    meta = {
        "pack": {
            "pack_format": 3,
            "description": "OnePiece Skyblock + Faithful 64x (1.12.2)"
        }
    }
    with open(mcmeta_path, "w", encoding="utf-8") as f:
        json.dump(meta, f, indent=2)

    # 3. Create required directory tree
    for d in [
        MODELS_CUSTOM_DIR, MODELS_MOD_DIR, MODELS_LQ_DIR,
        os.path.join(MODELS_MOD_DIR, "items"), os.path.join(MODELS_MOD_DIR, "abilities"),
        TEXTURES_CUSTOM_DIR, TEXTURES_MOD_DIR, TEXTURES_LQ_DIR,
        os.path.join(TEXTURES_MOD_DIR, "items"), os.path.join(TEXTURES_MOD_DIR, "abilities")
    ]:
        os.makedirs(d, exist_ok=True)

    for item in PK1_ITEMS:
        os.makedirs(os.path.join(MODELS_CUSTOM_DIR, item["sub"]), exist_ok=True)
        os.makedirs(os.path.join(TEXTURES_CUSTOM_DIR, item["sub"]), exist_ok=True)

    # 3b. Copy entire mod models and textures from Mine Mine no Mi
    src_mod_models = os.path.join(MOD_SOURCE_DIR, "mine-mine-no-mi-1.21.1-0.11.0.jar.src", "assets", "mineminenomi", "models")
    src_mod_tex = os.path.join(MOD_SOURCE_DIR, "mine-mine-no-mi-1.21.1-0.11.0.jar.src", "assets", "mineminenomi", "textures")
    dst_mod_models = os.path.join(RP_DIR, "assets", "mineminenomi", "models")
    dst_mod_tex = os.path.join(RP_DIR, "assets", "mineminenomi", "textures")
    if os.path.exists(src_mod_models):
        print(f"[Mod Models Transfer] Copying all 600 mod models to {dst_mod_models}...")
        shutil.copytree(src_mod_models, dst_mod_models, dirs_exist_ok=True)
    if os.path.exists(src_mod_tex):
        print(f"[Mod Textures Transfer] Copying all 1704 mod textures to {dst_mod_tex}...")
        shutil.copytree(src_mod_tex, dst_mod_tex, dirs_exist_ok=True)

    load_mod_models_cache()

    # 4. Collect PK2 items
    pk1_orig_keys = set()
    for item in PK1_ITEMS:
        pk1_orig_keys.add(item["key"].lower())
        if item.get("orig"):
            pk1_orig_keys.add(item["orig"].lower())
        for kw in item.get("kw", []):
            pk1_orig_keys.add(kw.lower())

    pk2_items = collect_pk2_items(pk1_orig_keys)
    print(f"[Catalog] Phân khu 1: {len(PK1_ITEMS)} items | Phân khu 2: {len(pk2_items)} items")

    # 5. Index mod assets for matching PK1
    png_index = scan_mod_assets()

    # Ensure handheld.json exists for weapons and tools
    handheld_file = os.path.join(MODELS_ITEM_DIR, "handheld.json")
    handheld_data = {
        "parent": "item/generated",
        "display": {
            "thirdperson_righthand": {
                "rotation": [ 0, -90, 55 ],
                "translation": [ 0, 4.0, 0.5 ],
                "scale": [ 0.85, 0.85, 0.85 ]
            },
            "thirdperson_lefthand": {
                "rotation": [ 0, 90, -55 ],
                "translation": [ 0, 4.0, 0.5 ],
                "scale": [ 0.85, 0.85, 0.85 ]
            },
            "firstperson_righthand": {
                "rotation": [ 0, -90, 25 ],
                "translation": [ 1.13, 3.2, 1.13 ],
                "scale": [ 0.68, 0.68, 0.68 ]
            },
            "firstperson_lefthand": {
                "rotation": [ 0, 90, -25 ],
                "translation": [ 1.13, 3.2, 1.13 ],
                "scale": [ 0.68, 0.68, 0.68 ]
            }
        }
    }
    with open(handheld_file, "w", encoding="utf-8") as f:
        json.dump(handheld_data, f, indent=2)

    # 6. Process PK1 items
    all_overrides = []

    for item in PK1_ITEMS:
        i_id = item["id"]
        dmg = math.floor(((i_id - 0.05) / 1561.0) * 10000000) / 10000000
        kind = item["kind"]
        key = item["key"]
        sub = item["sub"]

        model_logical_path = f"item/custom/{sub}/{key}"
        tex_logical_path = f"items/custom/{sub}/{key}"
        model_file = os.path.join(MODELS_CUSTOM_DIR, sub, f"{key}.json")
        tex_file = os.path.join(TEXTURES_CUSTOM_DIR, sub, f"{key}.png")

        all_overrides.append({
            "id": i_id,
            "damage": dmg,
            "model": model_logical_path
        })

        # --- A. MODEL CREATION ---
        if kind == "2d":
            item_disp = resolve_item_display(key, item.get("orig"), item.get("cat", ""))
            parent_model = "item/handheld" if is_handheld_item(key, item.get("cat", "")) else "item/generated"
            model_data = {
                "parent": parent_model,
                "textures": {
                    "layer0": tex_logical_path
                }
            }
            if item_disp:
                model_data["display"] = item_disp
        elif kind.startswith("3d_seaking"):
            state = "idle"
            if "attack" in kind: state = "attack"
            elif "roar" in kind: state = "roar"

            disp = dict(standard_3d_display_base)
            if state == "attack":
                disp.update({
                    "head": {"rotation": [0, 0, 0], "translation": [0, -25.5, -2.5], "scale": [2.35, 2.35, 2.35]},
                    "thirdperson_righthand": {"translation": [0, 3, 1], "scale": [0.55, 0.55, 0.55]},
                    "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.45, 0.45, 0.45]}
                })
            elif state == "roar":
                disp.update({
                    "head": {"rotation": [-22, 0, 0], "translation": [0, -25.5, 0], "scale": [2.2, 2.2, 2.2]},
                    "thirdperson_righthand": {"translation": [0, 3, 1], "scale": [0.55, 0.55, 0.55]},
                    "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.45, 0.45, 0.45]}
                })
            else:
                disp.update({
                    "head": {"rotation": [0, 0, 0], "translation": [0, -25.5, 0], "scale": [2.2, 2.2, 2.2]},
                    "thirdperson_righthand": {"translation": [0, 3, 1], "scale": [0.55, 0.55, 0.55]},
                    "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.45, 0.45, 0.45]}
                })

            model_data = {
                "textures": {
                    "texture": tex_logical_path,
                    "0": tex_logical_path,
                    "particle": tex_logical_path
                },
                "elements": generate_geometry_seaking(state),
                "display": disp
            }
        elif kind.startswith("3d_dummy"):
            var = "normal"
            if "boss" in kind: var = "boss"
            elif "logia" in kind: var = "logia"
            is_shake = ("shake" in kind)
            elements = generate_geometry_dummy(var)
            head_disp = {"rotation": [0, 0, 0], "translation": [0, -30.0, 0], "scale": [1.65, 1.65, 1.65]}
            if is_shake:
                head_disp["rotation"] = [-16, 10, 6]
                head_disp["translation"] = [0, -29.8, 1.2]

            disp = dict(standard_3d_display_base)
            disp.update({
                "head": head_disp,
                "thirdperson_righthand": {"scale": [0.55, 0.55, 0.55]},
                "gui": {"rotation": [15, -45, 0], "scale": [0.65, 0.65, 0.65]}
            })

            model_data = {
                "textures": {
                    "texture": tex_logical_path,
                    "0": tex_logical_path,
                    "particle": tex_logical_path
                },
                "elements": elements,
                "display": disp
            }
        elif kind == "3d_flag":
            elements = generate_geometry_flag()
            disp = dict(standard_3d_display_base)
            disp.update({
                "head": {"rotation": [0, 0, 0], "translation": [0, -29.5, 0], "scale": [1.75, 1.75, 1.75]},
                "thirdperson_righthand": {"rotation": [0, 90, -35], "translation": [0, 4, 2], "scale": [0.85, 0.85, 0.85]},
                "gui": {"rotation": [15, -45, 0], "scale": [0.65, 0.65, 0.65]}
            })
            model_data = {
                "textures": {
                    "texture": tex_logical_path,
                    "0": tex_logical_path,
                    "particle": tex_logical_path
                },
                "elements": elements,
                "display": disp
            }
        elif kind == "3d_chest":
            elements = generate_geometry_chest()
            disp = dict(standard_3d_display_base)
            disp.update({
                "head": {"rotation": [0, 0, 0], "translation": [0, 2.0, 0], "scale": [1.35, 1.35, 1.35]},
                "thirdperson_righthand": {"rotation": [0, 45, 0], "translation": [0, 1.5, 0], "scale": [0.45, 0.45, 0.45]},
                "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.62, 0.62, 0.62]}
            })
            model_data = {
                "textures": {
                    "texture": tex_logical_path,
                    "0": tex_logical_path,
                    "particle": tex_logical_path
                },
                "elements": elements,
                "display": disp
            }

        with open(model_file, "w", encoding="utf-8") as f:
            json.dump(model_data, f, indent=2)

        # --- B. TEXTURE CREATION ---
        if kind.startswith("3d_"):
            if "chest" in kind: pal = PALETTES_3D["df_chest"]
            elif "dragon" in item["key"]: pal = PALETTES_3D["flag_dragon"]
            elif "gold" in item["key"]: pal = PALETTES_3D["flag_gold"]
            elif "phoenix" in item["key"]: pal = PALETTES_3D["flag_phoenix"]
            elif "seaking" in kind: pal = PALETTES_3D["seaking"]
            elif "boss" in item["key"]: pal = PALETTES_3D["dummy_boss"]
            elif "logia" in item["key"]: pal = PALETTES_3D["dummy_logia"]
            else: pal = PALETTES_3D["dummy_normal"]

            png_data = generate_3d_material_atlas(pal)
        else:
            matched_png = match_mod_texture(item, png_index)
            if matched_png:
                png_data = process_2d_png(matched_png, target_size=64)
            else:
                png_data = generate_hd_pixel_art(64, 64, item.get("cat", "icon"), (180, 50, 50), (100, 20, 20), (255, 215, 0))

        with open(tex_file, "wb") as f:
            f.write(png_data)

    print(f"[OK] Completed {len(PK1_ITEMS)} items in Phân khu 1.")

    # 7. Process PK2 items
    print(f"[Processing PK2] Rescaling and writing {len(pk2_items)} items to 64x64 RGBA...")

    for item in pk2_items:
        i_id = item["id"]
        dmg = math.floor(((i_id - 0.05) / 1561.0) * 10000000) / 10000000

        all_overrides.append({
            "id": i_id,
            "damage": dmg,
            "model": item["model"]
        })

        # Model JSON file path
        rel_model = strip_prefix(item["model"], "item/").replace("/", os.sep) + ".json"
        full_model_path = os.path.join(MODELS_ITEM_DIR, rel_model)
        os.makedirs(os.path.dirname(full_model_path), exist_ok=True)

        parent_model = "item/handheld" if is_handheld_item(item["key"], item.get("cat", "")) else "item/generated"
        item_disp = resolve_item_display(item["key"], item.get("orig"), item.get("cat", ""))
        bb_model = get_mod_blockbench_elements(item["key"], item.get("orig"))

        if bb_model:
            model_json = {
                "credit": bb_model.get("credit", "Made with Blockbench"),
                "texture_size": bb_model.get("texture_size", [64, 64]),
                "textures": bb_model.get("textures", {}),
                "elements": bb_model.get("elements", []),
                "display": bb_model.get("display", item_disp or {})
            }
        else:
            model_json = {
                "parent": parent_model,
                "textures": {
                    "layer0": item["tex"]
                }
            }
            if item_disp:
                model_json["display"] = item_disp
        with open(full_model_path, "w", encoding="utf-8") as f:
            json.dump(model_json, f, indent=2)

        # Texture PNG path
        rel_tex = strip_prefix(item["tex"], "items/").replace("/", os.sep) + ".png"
        full_tex_path = os.path.join(TEXTURES_DIR, "items", rel_tex)
        os.makedirs(os.path.dirname(full_tex_path), exist_ok=True)

        png_data = process_2d_png(item["src"], target_size=64)
        if not png_data:
            png_data = generate_hd_pixel_art(64, 64, "icon", (70, 70, 80), (30, 30, 40), (200, 200, 200))

        with open(full_tex_path, "wb") as f:
            f.write(png_data)

    print(f"[OK] Completed {len(pk2_items)} items in Phân khu 2.")

    # 8. Generate diamond_hoe.json (Sorted strictly ascending by damage)
    all_overrides.sort(key=lambda x: x["damage"])

    hoe_data = {
        "parent": "item/handheld",
        "textures": {
            "layer0": "items/diamond_hoe"
        },
        "overrides": [
            {"predicate": {"damage": o["damage"]}, "model": o["model"]}
            for o in all_overrides
        ]
    }

    hoe_file = os.path.join(MODELS_ITEM_DIR, "diamond_hoe.json")
    with open(hoe_file, "w", encoding="utf-8") as f:
        json.dump(hoe_data, f, indent=2)

    print(f"[OK] Generated diamond_hoe.json with {len(all_overrides)} overrides (Sorted Ascending).")

    # 9. Generate Docs & Registry
    generate_documentation_and_registry(PK1_ITEMS, pk2_items)

    # 9.5 Custom Font Handling (Faithful 64x Smooth Vietnamese Bitmap Fonts)
    if ENABLE_CUSTOM_FONT:
        generate_vietnamese_bitmap_fonts(RP_DIR)
    else:
        font_tex_dir = os.path.join(RP_DIR, "assets", "minecraft", "textures", "font")
        font_meta_dir = os.path.join(RP_DIR, "assets", "minecraft", "font")
        if os.path.exists(font_tex_dir):
            shutil.rmtree(font_tex_dir)
            print("[Font Pipeline] Custom font disabled - cleaned textures/font directory.")
        if os.path.exists(font_meta_dir):
            shutil.rmtree(font_meta_dir)
            print("[Font Pipeline] Custom font disabled - cleaned font metadata directory.")

    # 10. Package ZIP archive
    print(f"\nCreating ZIP archive: {OUTPUT_ZIP}...")
    all_files = []
    for root, dirs, files in os.walk(RP_DIR):
        for file in sorted(files):
            abs_path = os.path.join(root, file)
            rel_path = os.path.relpath(abs_path, RP_DIR)
            normalized_arcname = rel_path.replace("\\", "/")
            all_files.append((abs_path, normalized_arcname))
    all_files.sort(key=lambda x: x[1])

    with zipfile.ZipFile(OUTPUT_ZIP, "w", zipfile.ZIP_DEFLATED) as zf:
        for abs_path, arcname in all_files:
            with open(abs_path, "rb") as f:
                data = f.read()
            zinfo = zipfile.ZipInfo(filename=arcname, date_time=(2026, 11, 15, 0, 0, 0))
            zinfo.compress_type = zipfile.ZIP_DEFLATED
            zf.writestr(zinfo, data)

    # 11. Compute SHA-1
    sha1 = hashlib.sha1()
    with open(OUTPUT_ZIP, "rb") as f:
        while chunk := f.read(65536):
            sha1.update(chunk)
    pack_hash = sha1.hexdigest()

    print("==================================================")
    print("RESOURCE PACK BUILD COMPLETED!")
    print(f"Output File: {OUTPUT_ZIP}")
    print(f"File Size:   {os.path.getsize(OUTPUT_ZIP):,} bytes")
    print(f"SHA-1 Hash:  {pack_hash}")
    print("==================================================")

    # 12. Update pack_hash.txt & server.properties
    hash_file = os.path.join(BASE_DIR, "pack_hash.txt")
    old_hash = ""
    if os.path.exists(hash_file):
        with open(hash_file, "r", encoding="utf-8") as f:
            old_hash = f.read().strip()

    with open(hash_file, "w", encoding="utf-8") as f:
        f.write(pack_hash + "\n")

    if os.path.exists(SERVER_PROPERTIES):
        with open(SERVER_PROPERTIES, "r", encoding="utf-8") as f:
            lines = f.readlines()
        with open(SERVER_PROPERTIES, "w", encoding="utf-8") as f:
            for line in lines:
                if line.startswith("resource-pack-sha1="):
                    f.write(f"resource-pack-sha1={pack_hash}\n")
                else:
                    f.write(line)
        print(f"[OK] Updated resource-pack-sha1 in server.properties -> {pack_hash}")

    # 13. Update README.md
    if os.path.exists(README_MD):
        with open(README_MD, "r", encoding="utf-8") as f:
            r_content = f.read()
        if old_hash and len(old_hash) == 40:
            r_content = r_content.replace(old_hash, pack_hash)
        r_content = re.sub(r"(`[0-9a-fA-F]{40}`)", f"`{pack_hash}`", r_content)
        with open(README_MD, "w", encoding="utf-8") as f:
            f.write(r_content)
        print(f"[OK] Updated SHA-1 in README.md -> {pack_hash}")

    # 14. Validation audit
    all_combined = PK1_ITEMS + pk2_items
    success = validate_resource_pack(all_combined)
    if not success:
        sys.exit(1)


if __name__ == "__main__":
    main()
