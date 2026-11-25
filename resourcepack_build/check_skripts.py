import os
import glob

sk_dir = r"d:\Skyblock\plugins\Skript\scripts"
keywords = ['setDurability', 'getType', 'setHelmetDropChance', '\t']

all_clean = True
for kw in keywords:
    matches = []
    for fpath in sorted(glob.glob(os.path.join(sk_dir, "*.sk"))):
        fname = os.path.basename(fpath)
        with open(fpath, 'r', encoding='utf-8') as f:
            for idx, line in enumerate(f, 1):
                if kw in line:
                    matches.append(f"{fname}:{idx}: {line.strip()}")
    print(f"Keyword {repr(kw)} -> Total: {len(matches)}")
    if matches:
        all_clean = False
        for m in matches:
            print(f"  {m}")

if all_clean:
    print("\n>>> ALL CHECKS PASSED: 0 matches for setDurability, getType, setHelmetDropChance, TAB <<<")
else:
    print("\n>>> SOME CHECKS FAILED <<<")
