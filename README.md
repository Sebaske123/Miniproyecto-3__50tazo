# 🃏 Cincuentazo

Juego de cartas desarrollado en Java con JavaFX donde los jugadores deben sobrevivir sin exceder la suma de 50 en la mesa.

## 🎯 Objetivo

Ser el último jugador en pie. La suma de las cartas en la mesa **no debe exceder 50**.

## 🎮 Reglas Básicas

### Valores de Cartas
- **2-8, 10**: Suman su valor
- **9**: No suma ni resta (0)
- **J, Q, K**: Restan 10
- **As**: Suma 1 o 10

### Mecánica
1. Cada jugador tiene 4 cartas
2. En tu turno, juegas una carta que no exceda 50
3. Tomas una carta del mazo
4. Si no puedes jugar ninguna carta, quedas eliminado

## 🛠️ Tecnologías

- Java SE 17+
- JavaFX + Scene Builder
- JUnit 5
- Git/GitHub

## 🏗️ Arquitectura

- **MVC**: Modelo-Vista-Controlador
- **Hilos**: Turnos de máquina (2-4s para jugar, 1-2s para tomar carta)
- **Excepciones propias**: Control de errores
- **Estructuras de datos**: Gestión dinámica del mazo

## 🚀 Ejecución

```bash
[git clone https://github.com/tu-usuario/cincuentazo.git
cd cincuentazo](https://github.com/Sebaske123/Miniproyecto-3__50tazo.git)
# Abrir en IntelliJ IDEA y ejecutar
```

## 📚 Funcionalidades

- ✅ Selección de 1-3 jugadores máquina
- ✅ Reparto automático de cartas
- ✅ Validación de jugadas
- ✅ Sistema de eliminación
- ✅ Declaración de ganador

## 👥 Equipo

- Juan Sebastian Tapia
- Jairo Andres Tegue


## 📄 Documentación

Código documentado con Javadoc en inglés. Pruebas unitarias incluidas.

---

**Mini Proyecto #3 - Programación Orientada a Eventos**
