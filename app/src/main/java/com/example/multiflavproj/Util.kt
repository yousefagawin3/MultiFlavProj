package com.example.multiflavproj

object Util {

    /**
     * this function will simply clean up and get the first equation from the given string
     *
     * this will only strictly return a valid value if there is a
     * "(number)(operator)(number) value in the string
     */
    fun getEquation(string: String)  : String {
        //removes spaces
        val filteredString = string.replace("\\s".toRegex(), "")
        var stringEquation = ""

        for (i in filteredString.indices) {
            if (filteredString[i].isDigit() || filteredString[i].toString().isBasicOperation()) {
                //getting first digit
                if((stringEquation.isEmpty() || stringEquation.all { it.isDigit() }) && filteredString[i].isDigit()) {
                    stringEquation += filteredString[i]
                }
                // getting operator
                else if (stringEquation.all { it.isDigit() } && filteredString[i].toString().isBasicOperation() && !filteredString[i-1].toString().isBasicOperation()) {
                    stringEquation += filteredString[i]
                }
                // getting second digit
                else if ((stringEquation.containsFirstValueAndOperation() || filteredString[i-1].isDigit()) && !filteredString[i].toString().isBasicOperation()) {
                    if(filteredString[i].isDigit()) {
                        stringEquation += filteredString[i]
                    }
                    //means that the function already found the first math equation
                    if(i+1 != filteredString.length) {
                        if(!filteredString[i+1].isDigit()) {
                            return stringEquation
                        }
                    }
                } else {
                    stringEquation = ""
                }
            } else {
                val values = stringEquation.split("+", "-", "*", "/").filterNot { it.isEmpty() }
                if(values.size == 2) {
                    break
                }
                else {
                    stringEquation = ""
                }
            }
        }
        return stringEquation
    }

    private fun String.containsFirstValueAndOperation() : Boolean {
        val firstValue = this.split("+", "-", "*", "/").filterNot { it.isEmpty() }
        //make sure that there is only one first value
        if(firstValue.isNotEmpty() && firstValue[0].all { it.isDigit() }) {
            return true
        }
        return false
    }


    /**
     * we are already assured that the equation here is always in this format
     * (value) (operator) (value)
     */
    fun String.computeEquation() : Int {

        var answer = 0

        val values = this.split("+", "-", "*", "/")

        when  {
            this.contains("+") -> {
                answer = values[0].toInt() + values[1].toInt()
            }

            this.contains("-") -> {
                answer = values[0].toInt() - values[1].toInt()
            }

            this.contains("*") -> {
                answer = values[0].toInt() * values[1].toInt()
            }

            this.contains("/") -> {
                answer = values[0].toInt() / values[1].toInt()
            }
        }

        return answer
    }


    fun String.isBasicOperation(): Boolean {
        return (this == "+" || this == "-" || this == "*" || this == "/")
    }
}