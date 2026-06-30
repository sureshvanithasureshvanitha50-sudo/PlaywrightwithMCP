from pathlib import Path
import shutil
import re

root = Path('target/allure-results')
filtered = Path('target/allure-results-playwright')
if filtered.exists():
    shutil.rmtree(filtered)
filtered.mkdir(parents=True, exist_ok=True)
patterns = [re.compile(rb'PlaywrightFormFlowTest'), re.compile(rb'fillDataEntryForm')]
selected = []
for f in root.rglob('*.json'):
    data = f.read_bytes()
    if any(p.search(data) for p in patterns):
        shutil.copy2(f, filtered / f.name)
        selected.append(f.name)
for name in ['executor.json', 'allure.properties']:
    src = root / name
    if src.exists():
        shutil.copy2(src, filtered / name)
print('selected files:')
for s in selected:
    print(s)
print('count', len(selected))
print('filtered files:')
for f in sorted(filtered.iterdir()):
    print(f.name)
