import os
import glob

for root, _, files in os.walk('app/src/main/java/com/example/ui'):
    for file in files:
        if not file.endswith('.kt'):
            continue
        filepath = os.path.join(root, file)
        with open(filepath, 'r') as f:
            content = f.read()
            
        if '@Preview' in content:
            # Replace MaterialTheme { with PreviewAppTheme { in preview functions
            # Actually, let's just make sure PreviewAppTheme is imported
            if 'PreviewAppTheme' not in content:
                content = content.replace('import androidx.compose.material3.MaterialTheme', 'import androidx.compose.material3.MaterialTheme\nimport com.example.ui.theme.PreviewAppTheme')
                content = content.replace('MaterialTheme {', 'PreviewAppTheme {')
                
                with open(filepath, 'w') as f:
                    f.write(content)
                print(f"Fixed {filepath}")
