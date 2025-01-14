package com.example.mobile

//
val codeUrl: (String?) -> String?
    get() = {
        it?.replace(
            '\u002f',// -> /
            '\u2204' // -> ∄
        )
            ?.replace(
                '\u003f',// -> ?
                '\u2203' // -> 	∃
            )
    }