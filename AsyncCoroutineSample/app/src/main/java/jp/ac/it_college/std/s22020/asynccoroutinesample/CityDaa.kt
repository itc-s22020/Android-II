package jp.ac.it_college.std.s22020.asynccoroutinesample

data class City(
    val name: String,
    val q: String
)

val cityList = listOf(
    City("大阪", "Osaka"),
    City("京都", "Kyoto"),
    City("東京", "Tokyo"),
    City("愛知", "Aichi"),
    City("和歌山", "Wakayama"),
    City("奈良", "Nara"),
    City("沖縄", "Okinawa"),
    City("北海道", "Hokkaido"),
)