package uz.aiml.myfactorial

sealed class State(
) {
}
class Error():State()
class Progress():State()
class Factorial(val value:String):State()