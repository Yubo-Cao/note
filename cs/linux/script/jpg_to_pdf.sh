#!/usr/bin/env zsh

pages=1
bookmarks="bookmarks.txt"
tmp_pdf="tmp.pdf"

if [ -f $bookmarks ]; then
    read -p "Do you want to overwrite the bookmarks file? [y/N] " -n 1 -r
    if [[ $REPLY =~ ^[Yy]$ ]]; then
        rm $bookmarks
    else
        echo "Aborting..."
        exit 1
    fi
fi

# Crop images to pdf
for img in *.jpg; do
    base=${img%%.jpg}
    convert $img -resize 2480x -extent 2480x3508 -gravity Center $base.pdf
done

# Concatenate pdfs and create bookmarks
for pdf in *.pdf; do
    cat <<- EOF >> $bookmarks
	BookmarkBegin
	BookmarkTitle: ${pdf%.pdf}
	BookmarkLevel: 1
	BookmarkPageNumber: $pages
	EOF
    page_number=$(pdftk $pdf dump_data | rg 'NumberOfPages: ' | awk '{print $2}')
    pages=$(($pages + $page_number))
done

pdftk *.pdf cat output $tmp_pdf
pdftk $tmp_pdf update_info $bookmarks output final.pdf

rm -f $tmp_pdf
rm -f $bookmarks
