package bexten.mobile.stuffed_fable_compose

import java.math.BigInteger

private val factorialConstants: Map<Int, BigInteger> = mapOf(
    1 to BigInteger("1"),
    2 to BigInteger("2"),
    3 to BigInteger("6"),
    4 to BigInteger("24"),
    5 to BigInteger("120"),
    6 to BigInteger("720"),
    7 to BigInteger("5040"),
    8 to BigInteger("40320"),
    9 to BigInteger("362880"),
    10 to BigInteger("3628800"),
    11 to BigInteger("39916800"),
    12 to BigInteger("479001600"),
    13 to BigInteger("6227020800"),
    14 to BigInteger("87178291200"),
    15 to BigInteger("1307674368000"),
    16 to BigInteger("20922789888000"),
    17 to BigInteger("355687428096000"),
    18 to BigInteger("6402373705728000"),
    19 to BigInteger("121645100408832000"),
    20 to BigInteger("2432902008176640000"),
    21 to BigInteger("51090942171709440000"),
    22 to BigInteger("1124000727777607680000"),
    23 to BigInteger("25852016738884976640000"),
    24 to BigInteger("620448401733239439360000"),
    25 to BigInteger("15511210043330985984000000"),
    26 to BigInteger("403291461126605635584000000"),
    27 to BigInteger("10888869450418352160768000000"),
    28 to BigInteger("304888344611713860501504000000"),
    29 to BigInteger("8841761993739701954543616000000"),
    30 to BigInteger("265252859812191058636308480000000"),
    31 to BigInteger("8222838654177922817725562880000000"),
    32 to BigInteger("265252859812191058636308480000000"),
    33 to BigInteger("8683317618811886495518194401280000000"),
    34 to BigInteger("295232799039604140847618609643520000000"),
    35 to BigInteger("10333147966386144929666651337523200000000")
)

fun chooseKFromN(k: Int, n: Int): BigInteger {
    val nMinusK = n - k;

    val factorialN = factorialConstants[n] ?: BigInteger("1")
    val factorialK = factorialConstants[k] ?: BigInteger("1")
    val factorialNMinusK = factorialConstants[nMinusK] ?: BigInteger("1")

    return factorialN / (factorialK * factorialNMinusK)
}
