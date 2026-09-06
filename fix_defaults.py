import os

for root, _, files in os.walk('app/src/main/java/com/example/ui'):
    for file in files:
        if not file.endswith('.kt'):
            continue
        filepath = os.path.join(root, file)
        with open(filepath, 'r') as f:
            content = f.read()
            
        new_content = content.replace('mainViewModel: MainViewModel?,', 'mainViewModel: MainViewModel? = null,')
        new_content = new_content.replace('mainViewModel: MainViewModel? = null = null', 'mainViewModel: MainViewModel? = null')
        new_content = new_content.replace('mainViewModel: MainViewModel?)', 'mainViewModel: MainViewModel? = null)')
        
        if new_content != content:
            with open(filepath, 'w') as f:
                f.write(new_content)
            print(f"Fixed defaults in {filepath}")
