package com.example.assist.domain.maintaince

enum class Part(val mileageResource: Int) {
    Antifreeze(250_000),
    AirFilter(30_000),
    PowerSteeringFluid(50_000),
    TransmissionFluid(50_000),
    EngineOilFilter(15_000),
    EngineOil(15_000),
    DriveBelt(70_000), // приводной ремень
    TimingBelt(130_000), // ремень ГРМ
    SparkPlug(30_000), // Свечи
    FuelFilter(30_000),
    BrakeFluid(55_000),
    BrakeDiscs(100_000),
    BrakeShoe(30_000), // тормозные колодки
    CabinAirFilter(15_000)
}