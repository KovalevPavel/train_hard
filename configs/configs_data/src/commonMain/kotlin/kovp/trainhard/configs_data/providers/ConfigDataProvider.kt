package kovp.trainhard.configs_data.providers

interface ConfigDataProvider<T> {
    val provider: suspend () -> T
}
