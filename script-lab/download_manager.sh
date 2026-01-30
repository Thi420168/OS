#!/bin/bash

for file in *; do
  if [ -f "$file" ]; then
    ext="${file##*.}"

    # Skip files without extension
    if [ "$file" = "$ext" ]; then
      continue
    fi

    mkdir -p "$ext"
    mv "$file" "$ext/"
  fi
done

echo "Downloads organized by file type."
