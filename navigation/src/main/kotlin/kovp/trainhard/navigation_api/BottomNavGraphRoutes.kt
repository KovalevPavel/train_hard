package kovp.trainhard.navigation_api

enum class BottomNavGraphRoutes(val route: String) {
    HOME("home"),
    STATISTICS("statistics"),
    PARAMETERS("parameters"),
    ;

    companion object {
        fun findByRoute(route: String?) = entries.firstOrNull {
            it.route.equals(route, ignoreCase = true)
        }
            ?: HOME
    }
}
