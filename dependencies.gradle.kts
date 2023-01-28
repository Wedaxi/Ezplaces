import java.io.FileInputStream
import java.util.Properties

extra.apply {
    set("koinVersion", "3.3.2")
    set("composeVersion", "1.2.0")
    set("accompanistVersion", "0.25.1")
    set("roomVersion", "2.4.3")
    set("activityVersion", "1.6.1")
    set("gsonVersion", "2.10.1")

    val apiPropertiesFile = rootProject.file("api.properties")
    val apiProperties = Properties()
    apiProperties.load(FileInputStream(apiPropertiesFile))

    set("apiProperties", apiProperties)

    set("adsEnabled", false)
}
