#!/usr/bin/bash

function set_resolution() {
    local x=${1:-1920}
    local y=${2:-1080}
    local refresh=${3:-60}
    local output=${4:-$(xrandr | grep ' connected' | head -n 1 | cut -d ' ' -f 1)}

    local mode="$(gtf $x $y $refresh | perl -nle 'print $1 if /^\s*Modeline\s*(.*)$/;' | head -n 1)"
    local mode_name="$(echo $mode | cut -d ' ' -f 1 | xargs)"

    echo " --newmode $mode" | xargs xrandr &>/dev/null

    xrandr --addmode $output $mode_name
    xrandr --output $output --mode $mode_name

    local sec=9
    echo -ne "Keep current configuration? (y/n) [$sec] "
    while [ $sec -gt 0 ]; do
        read -n 1 -t 1 answer
        if [ "$answer" == "y" ]; then
            echo ""
            return 0
        elif [ "$answer" == "n" ]; then
            echo ""
            xrandr --output $output --auto
            xrandr --delmode $output $mode_name
            xrandr --rmmode $mode_name
            return 1
        fi
        sec=$((sec - 1))
        echo -ne "\rKeep current configuration? (y/n) [$sec] "
    done

    xrandr --output $output --auto
    xrandr --delmode $output $mode_name
    xrandr --rmmode $mode_name
}

set_resolution 1920 1000 60
