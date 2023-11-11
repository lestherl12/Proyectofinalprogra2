package com.example.proyectofinal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.DrawerValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.Button
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
//nuevo icono
import androidx.compose.material.icons.filled.AddShoppingCart

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

data class Product(val name: String, val price: Double, val quantityInStock: Int, val category: String, val Desc :String,  val imageResId: Int)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val carroCompras by remember { mutableStateOf(ArrayList<Product>()) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
    ) {
        Navigation(navController = navController, carroCompras = carroCompras)
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = "PawMart Tienda de Mascotas", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)

            }
        },
        backgroundColor = Color.Yellow,
        contentColor = Color.Black
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    val items = listOf(

        NavigationItem.Mascotas,
        NavigationItem.Alimentos,
        NavigationItem.Accesorios,
        NavigationItem.Carrito,
    )

    Column(
        modifier = Modifier
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Yellow),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {

                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "PawMart",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: NavigationItem, selected: Boolean, onItemClick: (NavigationItem) -> Unit){
    val background = if(selected) R.color.teal_200 else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@Composable
fun MascotasScreen(carroCompras: ArrayList<Product>, onAddToCart: (Product) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val productos = listOf(
        Product("Perro Pastor Alemán", 2000.0, 1, "Perro pastor alemán pura sangre", "Descripción del Pastor Alemán", R.drawable.pastoraleman),
        Product("Perro French", 1000.0, 1, "Perro french de 2 meses de edad", "Descripción del Pastor Alemán", R.drawable.french),
        Product("Perro Pitbull ", 3000.0, 1, "Pitbull de 3 meses de edad, vacunado y desparasitado", "Descripción del Pastor Alemán", R.drawable.pitbull),
        Product("Perro Pastor Belga", 5000.0, 1, "Ya vacunado y en perfecta condicion", "Descripción del Pastor Alemán", R.drawable.pastorbelga),
        Product("Gato ", 200.0, 1, "Gato hembra", "Gato hembra de 3 meses", R.drawable.gato),
        Product("Cerdo Minipig ", 3000.0, 1, "Bonito Minipig", "Descripción del Pastor Alemán", R.drawable.minipig),
        Product("Pato", 600.0, 1, "Bonito pato", "Descripción del Pastor Alemán", R.drawable.pato),
        Product("Gallina", 5000.0, 1, "lista para empezar postura", "Descripción del Pastor Alemán", R.drawable.gallina),
        Product("Perro Chihuahua", 1400.0, 1, "Mascotas", "Chihuahua de 5 meses", R.drawable.chihuahua)

    )

    val filteredProductos = productos.filter { it.name.contains(searchText, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por nombre") },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White)
            )
        }

        items(filteredProductos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        // Acción al hacer clic en la tarjeta
                        onAddToCart(producto)
                    },
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(producto.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = producto.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Precio: Q${producto.price}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Descripción: ${producto.category}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botón de agregar al carrito con icono y texto al lado
                        Box(
                            modifier = Modifier
                                .clickable { onAddToCart(producto) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Agregar al Carrito",
                                    fontSize = 12.sp,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun AlimentosScreen(carroCompras: ArrayList<Product>, onAddToCart: (Product) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val productos = listOf(
        Product("Alimento Para Perro Adulto Rambocan", 750.0, 1, "Ideal para que nuestros perros esten saludables ", "Descripción del Pastor Alemán", R.drawable.rambocan),
        Product("Alimento para gato Whiscas", 1000.0, 1, "Alimento premium para los concentidos", "Descripción del Pastor Alemán", R.drawable.whiscas),
        Product("Alimento para Conejos", 500.0, 1, "Para la buena salud de nuestros conejos", "Descripción del Pastor Alemán", R.drawable.concentradoconejos),
        Product("Alimento para Gallinas", 250.0, 1, "Alimento para gallinas ponedoras Marca Nutramix", "Descripción del Pastor Alemán", R.drawable.nutramix),
        Product("Alimento para cerdos", 400.0, 1, "Ideal para la etapa de engorde de engorde de los cerdos", "Descripción del Pastor Alemán", R.drawable.concentradocerdos)

    )

    val filteredProductos = productos.filter { it.name.contains(searchText, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por nombre") },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White
                )
            )
        }

        items(filteredProductos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {  },
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(producto.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = producto.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Precio: $${producto.price}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Descripción: ${producto.category}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botón de agregar al carrito con icono y texto al lado
                        Box(
                            modifier = Modifier
                                .clickable { onAddToCart(producto) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Agregar al Carrito",
                                    fontSize = 12.sp,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccesoriosScreen(carroCompras: ArrayList<Product>, onAddToCart: (Product) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    val productos = listOf(
        Product("Cadena para perro ", 40.0, 1, "Cadena mediana para perros  ", "Descripción del Pastor Alemán", R.drawable.cadenaperro),
        Product("Collar para perro ", 30.0, 1, "Collar para perro ajustable ", "Descripción del Pastor Alemán", R.drawable.collarperrro),
        Product("Collar para gato ", 20.0, 1, "Collar para gato ajustable", "Descripción del Pastor Alemán", R.drawable.collargato),
        Product("Camisa para gatos ", 50.0, 1, "Camisa pequeña para gato ", "Descripción del Pastor Alemán", R.drawable.camisagato),
        Product("Cepillo para animes ", 30.0, 1, "Cepillo deal para cualquier tipo de animal", "Descripción del Pastor Alemán", R.drawable.peine)

    )

    val filteredProductos = productos.filter { it.name.contains(searchText, ignoreCase = true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por nombre") },
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color.White
                )
            )
        }

        items(filteredProductos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { /* Acción al hacer clic en la tarjeta */ },
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(producto.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = producto.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Precio: Q${producto.price}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Descripción: ${producto.category}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botón de agregar al carrito con icono y texto al lado
                        Box(
                            modifier = Modifier
                                .clickable { onAddToCart(producto) },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AddShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Agregar al Carrito",
                                    fontSize = 12.sp,
                                    color = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarroComprasScreen(carroCompras: ArrayList<Product>, onRemoveLastFromCart: () -> Unit) {
    var total: Double by remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top, // Cambiamos la disposición vertical a Top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp) // Agregamos un padding vertical
        )

        // Muestra la lista de productos en el carrito
        LazyColumn(
            modifier = Modifier.weight(1f) // Añadimos un weight para ocupar el espacio restante
        ) {
            items(carroCompras) { producto ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${producto.name} - Q${producto.price}")

                    Button(
                        onClick = {
                            total -= producto.price
                            onRemoveLastFromCart()
                        },
                        modifier = Modifier.size(90.dp)
                    ) {
                        Text("Eliminar", color = Color.White)
                    }
                }

                // Suma el precio de cada producto al total
                total
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra el total acumulado
        Text(
            text = "Total: Q$total",
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

    Button(
        onClick = { total = carroCompras.sumByDouble { it.price } },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text("Sumar Total",  color = Color.White)
    }
}


@Composable
fun Navigation(navController: NavHostController, carroCompras: ArrayList<Product>) {
    NavHost(navController = navController, startDestination = NavigationItem.Mascotas.route) {
        composable(NavigationItem.Mascotas.route) {
            MascotasScreen(
                carroCompras = carroCompras,
                onAddToCart = { producto -> carroCompras.add(producto) }
            )
        }
        composable(NavigationItem.Alimentos.route) {
            AlimentosScreen(
                carroCompras = carroCompras,
                onAddToCart = { producto -> carroCompras.add(producto) }
            )
        }
        composable(NavigationItem.Accesorios.route) {
            AccesoriosScreen(
                carroCompras = carroCompras,
                onAddToCart = { producto -> carroCompras.add(producto) }
            )
        }
        composable(NavigationItem.Carrito.route) {
            CarroComprasScreen(
                carroCompras = carroCompras,
                onRemoveLastFromCart = {
                    if (carroCompras.isNotEmpty()) {
                        carroCompras.removeAt(carroCompras.size - 1)
                    }
                }
            )
        }
    }
}
