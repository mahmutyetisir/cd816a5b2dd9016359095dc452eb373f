package com.myetisir.spacetransporter.data.model

sealed class TravelStatus<T> {
    class Complated<T> : TravelStatus<T>()
    data class Success<T>(val data: T) : TravelStatus<T>()
    data class Error<T>(val exception: Throwable) : TravelStatus<T>()
    class Loading<T> : TravelStatus<T>()
}

sealed class TravelError {
    object CapacityError : Throwable("Yeterli giysiniz bulunmadığı için görev yapılamadı")
    object EUSError : Throwable("Yeterli zamanınız bulnmadığı için görev yapılamadı")
    object CrashError : Throwable("Geminizin dayanıklılığı kalmadığı için göreviniz yapılamadı")
}


data class TravelMission(
    var transporter: Transporter,
    var transporterCrashRate: Int,
    var currentStation: Station,
    var destinationStation: Station
)