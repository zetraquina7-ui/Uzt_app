#!/bin/bash
cd app/src/main/res/drawable || exit
shopt -s nullglob
for f in *.png *.jpg *.jpeg; do
    if [ -f "$f" ]; then
        filename="${f%.*}"
        echo "Converting $f..."
        cwebp -q 80 "$f" -o "${filename}.webp" > /dev/null 2>&1
        if [ $? -eq 0 ] && [ -f "${filename}.webp" ]; then
            rm -f "$f"
        else
            echo "Failed to convert $f"
        fi
    fi
done
echo "Done converting!"
