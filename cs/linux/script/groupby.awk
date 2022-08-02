# grouping by "name" and sum values columns "qty" and "price"
BEGIN {
    FS = OFS = ","
}

NR == 1 {
    print $0
    next
}

NR > 1 {
    ori = price[$1 "," $2]
    if (ori == "") {
        price[$1 "," $2] = $3 "," $4
    } else {
        split(ori, a, ",")
        price[$1 "," $2] = a[1] + $3 "," a[2] + $4
    }
}

END {
    for (name in price) {
        print name, price[name]
    }
}