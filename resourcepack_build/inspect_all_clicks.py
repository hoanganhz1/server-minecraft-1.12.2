import os
import re

scripts_dir = r"d:\Skyblock\plugins\Skript\scripts"

def analyze_all_clicks():
    results = {}
    for fn in sorted(os.listdir(scripts_dir)):
        if not fn.endswith(".sk"):
            continue
        path = os.path.join(scripts_dir, fn)
        with open(path, "r", encoding="utf-8") as f:
            c = f.read()

        # Find all inventory click handlers
        click_blocks = re.findall(r'on inventory click\s*:(.*?)(?=\non |\ncommand |\Z)', c, re.DOTALL)
        for b in click_blocks:
            # find title checks
            titles = re.findall(r'(\bif\s+.*?getTitle\(\).*?:\s*|\bif\s+.*?title of.*?:)', b)
            # find slots
            slots = re.findall(r'if {_slot} is ([0-9]+)|else if {_slot} is ([0-9]+)', b)
            actions = re.findall(r'(open[A-Za-z0-9_]+GUI\(.*?\)|craft[A-Za-z0-9_]+\(.*?\)|upgrade[A-Za-z0-9_]+\(.*?\)|take[A-Za-z0-9_]+\(.*?\))', b)
            if titles or slots or actions:
                print(f"=== {fn} ===")
                print("Titles checked:", [t.strip() for t in titles])
                print("Slots handled:", [a or s for a, s in slots])
                print("Actions:", actions[:6])
                print()

analyze_all_clicks()
