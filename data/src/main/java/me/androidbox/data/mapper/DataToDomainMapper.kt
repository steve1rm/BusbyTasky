package me.androidbox.data.mapper

fun interface DataToDomainMapper<in E, out M> {
    operator fun invoke(entity: E): M
}