package day15

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay15 {

    @Test
    fun `it should hash file`() {
        expectThat(HashMap().hash("HASH")).isEqualTo(52)
        expectThat(HashMap().hash("rn=1")).isEqualTo(30)
    }

    @Test
    fun `it should hash csv`() {
        expectThat(HashMap().hash("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(","))).isEqualTo(1320)
    }

    @Test
    fun `it should set property`() {
        val map = HashMap()
        map.apply("rn=1")
        val box0 = map.map.get(0).toList()
        expectThat(box0).isEqualTo(listOf("rn" to 1))
    }

    @Test
    fun `it should remove property`() {
        val map = HashMap()
        map.apply("pc=1")
        map.apply("ot=2")
        map.apply("ab=3")
        map.apply("ot-")
        val box0 = map.map.get(3).toList()
        expectThat(box0).isEqualTo(listOf("pc" to 1, "ab" to 3))
    }

    @Test
    fun `it should update property in place`() {
        val map = HashMap()
        map.apply("pc=1")
        map.apply("ot=2")
        map.apply("ab=3")
        map.apply("ot=4")
        val box0 = map.map.get(3).toList()
        expectThat(box0).isEqualTo(listOf("pc" to 1, "ot" to 4, "ab" to 3))
    }

    @Test
    fun `it should apply all`() {
        val map = HashMap()
        map.applyAll(listOf("pc=1", "ot=2", "ab=3", "ot=4"))
        val box0 = map.map.get(3).toList()
        expectThat(box0).isEqualTo(listOf("pc" to 1, "ot" to 4, "ab" to 3))
    }

    @Test
    fun `it should handle power`() {
        val map = HashMap()
        map.applyAll("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".split(","))
        expectThat(map.power()).isEqualTo(145)
    }

}