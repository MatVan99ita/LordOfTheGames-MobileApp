package com.example.lordofthegames


class Pair<X, Y>(var x: X?, var y: Y?) {

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (x?.hashCode() ?: 0)
        result = prime * result + (y?.hashCode() ?: 0)
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as Pair<*, *>
        if (x == null) {
            if (other.x != null) return false
        } else if (x != other.x) return false
        return if (y == null) {
            other.y == null
        } else y == other.y
    }

    override fun toString(): String {
        return "Pair [x=$x, y=$y]"
    }
}