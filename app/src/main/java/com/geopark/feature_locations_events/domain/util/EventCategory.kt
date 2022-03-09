package com.geopark.feature_locations_events.domain.util

enum class EventCategory(val categoryName: String, val categoryId : String){
    ALL("Wszystkie",""),
    ADULTS("Dla dorosłych","dla-doroslych"),
    FAMILIES("Dla rodzin","dla-rodzin"),
    KIDS("Z dziećmi","z-dziecmi"),
    GEOLOGY("Geologia i minerały","geologia-mineraly"),
    HISTORY("Historia","historia"),
    CULINARY("Kulinaria","kulinaria"),
    ROOF("Pod dachem","pod-dachem"),
    PASSPORT("Paszport Odkrywcy","paszportkww"),
    NATURE("Przyrodnicze","przyrodnicze"),
    CRAFTS("Rzemiosło i sztuka","rzemioslo-sztuka"),
    SPORT("Sport","sport"),
    FIELD("W terenie","w-terenie")
}