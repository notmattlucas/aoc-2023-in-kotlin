package day12

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay12 {

    @Test
    fun `it should parse line`() {
        val search =  Search.from("#.#.### 1,1,3")
        assert(search.initRow == "#.#.###")
        assert(search.initGroups == listOf(1, 1, 3))
    }

    @Test
    fun `it should search for 1 group` () {
        val search = Search.from("???.### 1,1,3")
        expectThat(search.search()).isEqualTo(listOf("#.#.###"))
    }

    @Test
    fun `it should search for 4 groups` () {
        val search = Search.from(".??..??...?##. 1,1,3")
        expectThat(search.search()).isEqualTo(listOf("#.#.###"))
    }

}