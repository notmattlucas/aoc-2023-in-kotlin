package day20

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class TestDay20 {

    private val downstream = Mockito.mock(Module::class.java)

    private val downstream2 = Mockito.mock(Module::class.java)

    @BeforeEach
    fun setup() {
        whenever(downstream.backlink(anyOrNull())).thenAnswer { }
        whenever(downstream2.backlink(anyOrNull())).thenAnswer { }
    }

    @Test
    fun `flip flop modules are initially off`() {
        val flipFlop = FlipFlop("test")
        expectThat(flipFlop.on).isFalse()
    }

    @Test
    fun `flip flop off ignores high pulse`() {
        val flipFlop = FlipFlop("test")
        flipFlop.connectTo(downstream)
        val commands = flipFlop.tick("in", 1)
        expectThat(commands).isEmpty()
    }

    @Test
    fun `flip flop alternates when receiving low pulse`() {
        val flipFlop = FlipFlop("test")
        flipFlop.connectTo(downstream)

        var commands = flipFlop.tick("in", 0)
        expectThat(flipFlop.on).isTrue()
        expectThat(commands).isEqualTo(listOf(Command(downstream, "test", 1)))

        commands = flipFlop.tick("in", 0)
        expectThat(flipFlop.on).isFalse()
        expectThat(commands).isEqualTo(listOf(Command(downstream, "test", 0)))
    }

    @Test
    fun `broadcast module sends message to all downstream`() {
        val broadcast = Broadcast("test")
        broadcast.connectTo(downstream)
        broadcast.connectTo(downstream2)

        var commands = broadcast.tick("in", 0)
        expectThat(commands).isEqualTo(listOf(Command(downstream, "test", 0), Command(downstream2, "test", 0)))

        commands = broadcast.tick("in", 1)
        expectThat(commands).isEqualTo(listOf(Command(downstream, "test", 1), Command(downstream2, "test", 1)))
    }

    @Test
    fun `conjunction module ANDs all inputs`() {
        val in1 = Broadcast("in1")
        val in2 = Broadcast("in2")
        val conj = Conjunction("conj")
        in1.connectTo(conj)
        in2.connectTo(conj)
        conj.connectTo(downstream)

        expectThat(conj.tick("in1", 0)).isEqualTo(listOf(Command(downstream, "conj", 1)))
        expectThat(conj.tick("in1", 1)).isEqualTo(listOf(Command(downstream, "conj", 1)))
        expectThat(conj.tick("in2", 1)).isEqualTo(listOf(Command(downstream, "conj", 0)))
    }

    @Test
    fun `it should handle example one`() {
        val engine = Engine.parse(listOf(
            "broadcaster -> a, b, c",
            "%a -> b",
            "%b -> c",
            "%c -> inv",
            "&inv -> a"
        ))
        (1..1000).forEach { _ -> engine.press() }
        expectThat(engine.value()).isEqualTo(32000000)
    }

    @Test
    fun `it should handle example two`() {
        val engine = Engine.parse(listOf(
            "broadcaster -> a",
            "%a -> inv, con",
            "&inv -> b",
            "%b -> con",
            "&con -> output"
        ))
        (1..1000).forEach { _ -> engine.press() }
        expectThat(engine.value()).isEqualTo(11687500)
    }

}