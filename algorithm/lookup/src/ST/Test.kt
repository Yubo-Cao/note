package ST

fun testST(st: ST<String, String>) {
    assert(st.isEmpty()) { "ST should be empty" }
    assert("" !in st) { "ST should not contain empty string" }
    st["key"] = "value"
    st["test"] = "test"
    st["test2"] = "test2"
    assert(st["key"] == "value") { "Expect 'value'" }
    assert(st["test"] == "test") { "Expect 'test'" }
    st["test"] = "test3"
    assert(st["test"] == "test3") { "Expect 'test3'" }
    assert(st.size == 3) { "Expect size 3" }
    assert(!st.isEmpty()) { "Expect not empty" }
    assert("test" in st) { "Expect 'test' in ST" }
}

fun main() {
    testST(SequentialSearchST())
}