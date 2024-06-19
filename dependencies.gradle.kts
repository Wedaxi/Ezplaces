import java.io.FileInputStream
import java.util.Properties

extra.apply {
    set("koinVersion", "3.5.6")
    set("coreKtsVersion", "1.13.1")
    set("composeVersion", "1.6.8")
    set("accompanistVersion", "0.34.0")
    set("roomVersion", "2.6.1")
    set("activityVersion", "1.9.0")
    set("gsonVersion", "2.11.0")
    set("wearComposeVersion", "1.1.1")
    set("lifecycleVersion", "2.8.2")

    val apiPropertiesFile = rootProject.file("api.properties")
    val apiProperties = Properties()
    apiProperties.load(FileInputStream(apiPropertiesFile))

    set("apiProperties", apiProperties)

    set("adsEnabled", false)
}
