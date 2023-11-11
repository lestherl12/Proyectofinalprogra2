package com.example.proyectofinal

sealed class NavigationItem(var route: String, var icon: Int, var title: String)
{
    object Mascotas : NavigationItem("Mascotas", android.R.drawable.ic_menu_myplaces, "Mascotas")
    object Alimentos : NavigationItem("Alimentos", android.R.drawable.ic_menu_search, "Alimentos")
    object Accesorios : NavigationItem("Accesorios", android.R.drawable.ic_menu_agenda, "Accesorios")
    object Carrito : NavigationItem("Carrito", android.R.drawable.ic_menu_add, "Carrito")
}
