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
    fun `it should search groups` () {
        expectThat(Search.from("???.### 1,1,3").search().size).isEqualTo(1)
        expectThat(Search.from(".??..??...?##. 1,1,3").search().size).isEqualTo(4)
        expectThat(Search.from("?#?#?#?#?#?#?#? 1,3,1,6").search().size).isEqualTo(1)
        expectThat(Search.from("????.#...#... 4,1,1").search().size).isEqualTo(1)
        expectThat(Search.from("????.######..#####. 1,6,5").search().size).isEqualTo(4)
        println(Search.from("?###???????? 3,2,1").search())
        expectThat(Search.from("?###???????? 3,2,1").search().size).isEqualTo(10)
    }

    @Test
    fun `it should handle part1`() {
        val input = listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1"
        )
        expectThat(part1(input)).isEqualTo(21)
    }

    @Test
    fun `it should search folded groups` () {
        expectThat(Search.fromFolded("???.### 1,1,3").search().size).isEqualTo(1)
        expectThat(Search.fromFolded(".??..??...?##. 1,1,3").search().size).isEqualTo(16384)
        expectThat(Search.fromFolded("?#?#?#?#?#?#?#? 1,3,1,6").search().size).isEqualTo(1)
        expectThat(Search.fromFolded("????.#...#... 4,1,1").search().size).isEqualTo(16)
        expectThat(Search.fromFolded("????.######..#####. 1,6,5").search().size).isEqualTo(2500)
        expectThat(Search.fromFolded("?###???????? 3,2,1").search().size).isEqualTo(506250)
    }

}