## Entrega de Proyecto Final

## Objetivo general

Desarrollar una API RESTful completa en Java utilizando Spring Boot y MySQL para gestionar un sistema de E-commerce, integrándose con una aplicación frontend. La aplicación deberá aplicar correctamente conceptos avanzados de programación en Java, arquitectura REST, bases de datos relacionales, validaciones, excepciones y organización modular.


Este proyecto es una API REST desarrollada con **Spring Boot**, **JPA/Hibernate** y **Lombok** para gestionar el catálogo de productos y el flujo de procesamiento de pedidos en una plataforma de comercio electrónico.


## Arquitectura y Estructura del Código

El backend sigue un patrón de diseño por capas estándar de Spring Boot para mantener la separación de responsabilidades:

```
src/main/java/com/techlab/ecommerce/
│
├── controllers/          # Capa de presentación (Endpoints REST)
│   ├── PedidoController.java
│   └── ProductoController.java
│
├── services/             # Capa de negocio (Lógica, transacciones y reglas)
│   └── PedidoService.java
│
├── repositories/         # Capa de acceso a datos (Abstracción de Base de Datos)
│   ├── PedidoRepository.java
│   └── ProductoRepository.java
│
├── model/                # Capa de dominio (Entidades JPA mapeadas a tablas)
│   ├── Pedido.java
│   ├── LineaPedido.java
│   └── Producto.java
│
└── excepciones/          # Manejo de errores personalizados
    └── StockInsuficienteException.java
```

## Detalle de Endpoints
## Productos (/api/productos)

1. Obtener todos los productos

    Método: GET

    URL: /api/productos

    Descripción: Recupera la lista completa de productos disponibles en la tienda.

    Respuesta Exitosa (200 OK):

 ```json 
    [
      {
        "id": 1,
        "title": "Teclado Mecánico RGB",
        "description": "Teclado con switches mecánicos configurables.",
        "price": 85.50,
        "thumbnail": "[https://url-imagen.com/teclado.jpg](https://url-imagen.com/teclado.jpg)",
        "stock": 42,
        "category": "Periféricos"
      }
    ]
```
2. Guardar un producto nuevo

    Método: POST

    URL: /api/productos

    Cuerpo de la Petición (Cuerpo JSON):
    
```json
    {
      "title": "Mouse Gamer Inalámbrico",
      "description": "Mouse óptico de alta precisión.",
      "price": 45.00,
      "thumbnail": "[https://url-imagen.com/mouse.jpg](https://url-imagen.com/mouse.jpg)",
      "stock": 20,
      "category": "Periféricos"
    }
```
    Respuesta Exitosa (200 OK): Devuelve el objeto guardado incluyendo su id asignado por la base de datos.

## Pedidos (/api)
1. Crear un nuevo Pedido desde el carrito

    Método: POST

    URL: /api/pedidos

    Descripción: Procesa la compra. Valida la existencia del producto, verifica si hay stock suficiente, descuenta el stock en base de datos de manera atómica (@Transactional) y calcula automáticamente el monto total.

    Cuerpo de la Petición (Cuerpo JSON):
   
```json
    [
      {
        "productoId": 1,
        "cantidad": 2
      }
    ]
```
    Respuesta Exitosa (200 OK):
```json
    {
      "id": 101,
      "usuarioId": 5,
      "fecha": "2026-07-08T22:40:16",
      "estado": "PENDIENTE",
      "total": 171.0,
      "lineas": [
        {
          "id": 1,
          "productoId": 1,
          "cantidad": 2,
          "precioUnitario": 85.50
        }
      ]
    }
```
    Respuestas de Error:

        500 Internal Server Error (o el manejador global configurado): Si el stock es inferior a la cantidad solicitada (StockInsuficienteException) o si el ID del producto no existe.

2. Historial de pedidos por usuario

    Método: GET

    URL: /api/usuarios/{id}/pedidos

    Descripción: Recupera todos los pedidos asociados a un identificador de usuario único. (Por defecto en el modelo actual de pruebas, los nuevos pedidos se crean para el usuarioId = 5L).

    Ejemplo: /api/usuarios/5/pedidos

    Respuesta Exitosa (200 OK):
    
```json
    [
      {
        "id": 101,
        "usuarioId": 5,
        "fecha": "2026-07-08T22:40:16",
        "estado": "PENDIENTE",
        "total": 171.0,
        "lineas": [
          {
            "id": 1,
            "productoId": 1,
            "cantidad": 2,
            "precioUnitario": 85.50
          }
        ]
      }
    ]
```
