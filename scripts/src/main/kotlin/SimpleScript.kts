// src/main/scripts/SimpleScript.kts

import com.google.gson.Gson
import com.google.gson.JsonObject

data class Person(val name: String, val age: Int)
data class Name(val first: String, val last: String)

fun main() {
    val person = Person(name = "{\"first\":\"Alice\",\"last\":\"White\"}", age = 30)
    val gson = Gson()

    // Parse the "name" field into a Name object
    val nameObject = gson.fromJson(person.name, Name::class.java)

    // Create a JsonObject and manually add the fields
    val jsonObject = JsonObject()
    jsonObject.add("name", gson.toJsonTree(nameObject))
    jsonObject.addProperty("age", person.age)

    // Output the final JSON string
    println(gson.toJson(jsonObject))
    // Output: {"name":{"first":"Alice","last":"White"},"age":30}
}

main()