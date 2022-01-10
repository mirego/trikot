package com.mirego.trikot.viewmodels.properties

import spock.lang.Specification

class ColorTests extends Specification {

    def '''
        when Creating a color
        then basic conversion properties works well
        '''() {
        when:
        Color color = new Color(255, 11, 56, 0.9f)

        then:
        color.hex() == "FF0B38"
        color.hex("#") == "#FF0B38"
        color.hexARGB("#") == "#E6FF0B38"
        color.hexRGBA("#") == "#FF0B38E6"
    }
}
