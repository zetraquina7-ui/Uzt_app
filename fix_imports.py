import os

for root, _, files in os.walk('app/src/main/java/com/example/ui'):
    for file in files:
        if not file.endswith('.kt'):
            continue
        filepath = os.path.join(root, file)
        with open(filepath, 'r') as f:
            content = f.read()
            
        if 'PreviewAppTheme {' in content and 'import com.example.ui.theme.PreviewAppTheme' not in content:
            content = content.replace('package com.example.ui.screens', 'package com.example.ui.screens\n\nimport com.example.ui.theme.PreviewAppTheme')
            with open(filepath, 'w') as f:
                f.write(content)
            print(f"Fixed import in {filepath}")
