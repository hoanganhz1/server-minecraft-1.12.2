import os
import sys

def check_file(path):
    issues = []
    
    # 1. Check BOM
    with open(path, 'rb') as f:
        raw = f.read()
        if raw.startswith(b'\xef\xbb\xbf'):
            issues.append("BOM detected (must be UTF-8 without BOM)")
            
    try:
        content = raw.decode('utf-8')
    except UnicodeDecodeError as e:
        issues.append(f"Unicode decode error: {e}")
        return issues
        
    lines = content.splitlines()
    for idx, line in enumerate(lines, start=1):
        # 2. Check Tab
        if '\t' in line:
            issues.append(f"Line {idx}: Tab character '\\t' detected")
            
        # 3. Check forbidden methods
        for forbidden in ['.getType(', '.setDurability(', '.setHelmetDropChance(']:
            if forbidden in line:
                issues.append(f"Line {idx}: Forbidden call '{forbidden}' detected")
                
        # 4. Check 4-byte emoji (surrogate pairs / beyond BMP)
        for char in line:
            if ord(char) > 0xFFFF:
                issues.append(f"Line {idx}: 4-byte character '{char}' (U+{ord(char):04X}) detected outside BMP")

    return issues

def main():
    target_dir = os.path.join("plugins", "Skript", "scripts")
    files = [f for f in os.listdir(target_dir) if f.endswith('.sk')]
    files.sort()
    
    print(f"Checking {len(files)} Skript files in {target_dir}...")
    total_issues = 0
    
    for f in files:
        full_path = os.path.join(target_dir, f)
        issues = check_file(full_path)
        if issues:
            print(f"[FAIL] {f}:")
            for issue in issues:
                print(f"  - {issue}")
            total_issues += len(issues)
        else:
            print(f"[PASS] {f}")
            
    print("-" * 50)
    if total_issues == 0:
        print("ALL SKRIPT FILES PASSED TECHNICAL VERIFICATION!")
    else:
        print(f"TOTAL ISSUES FOUND: {total_issues}")

if __name__ == "__main__":
    main()
