package test;

import controllers.BrandController;
import models.Brand;
import validaciones.Validators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class BrandControllerTest {

    private BrandController controller;
    private static final String FILA_ESTUDIANTE = cargarFilaEstudiante();

    /**
     * Lee la fila del estudiante desde el archivo student.env
     */
    private static String cargarFilaEstudiante() {
        try (BufferedReader reader = new BufferedReader(new FileReader("student.env"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("FILA_ESTUDIANTE= D")) {
                    return linea.substring("FILA_ESTUDIANTE= D".length()).trim();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("ERROR: No se pudo leer el archivo student.env: " + e.getMessage());
        }
        return null;
    }

    /**
     * Invoca un método por reflexión y maneja errores si no existe
     */
    private void invocarMetodoOrdenamiento(String nombreMetodo, Brand[] paraOrdenar) {
        try {
            Method metodo = BrandController.class.getMethod(nombreMetodo, Brand[].class);
            metodo.invoke(controller, (Object) paraOrdenar);
        } catch (NoSuchMethodException e) {
            org.junit.jupiter.api.Assertions.fail(
                    String.format("ERROR: El método '%s(Brand[])' no existe en BrandController.\n" +
                            "Debe implementar este método según su fila asignada.", nombreMetodo));
        } catch (Exception e) {
            org.junit.jupiter.api.Assertions.fail(
                    String.format("ERROR al ejecutar el método '%s': %s", nombreMetodo, e.getMessage()));
        }
    }

    /**
     * Invoca el método de búsqueda binaria por reflexión
     */
    private Brand invocarBusquedaBinaria(Brand[] paraOrdenar, int validYears, boolean isAscending) {
        try {
            Method metodo = BrandController.class.getMethod("binarySearchByValidYears",
                    Brand[].class, int.class, boolean.class);
            return (Brand) metodo.invoke(controller, paraOrdenar, validYears, isAscending);
        } catch (NoSuchMethodException e) {
            org.junit.jupiter.api.Assertions.fail(
                    "ERROR: El método 'binarySearchByValidYears(Brand[], int, boolean)' no existe en BrandController.\n"
                            +
                            "Debe implementar este método según las especificaciones.");
            return null;
        } catch (Exception e) {
            org.junit.jupiter.api.Assertions.fail(
                    String.format("ERROR al ejecutar búsqueda binaria: %s", e.getMessage()));
            return null;
        }
    }

    @BeforeEach
    public void setUp() {
        controller = new BrandController();

        // Validar que la fila esté configurada
        if (FILA_ESTUDIANTE == null || FILA_ESTUDIANTE.trim().isEmpty()) {
            throw new RuntimeException(
                    "ERROR: Debe configurar FILA_ESTUDIANTE en el archivo student.env con su fila asignada (A, B, C o D).");
        }

        String fila = FILA_ESTUDIANTE.trim().toUpperCase();
        if (!fila.equals("A") && !fila.equals("B") && !fila.equals("C") && !fila.equals("D")) {
            throw new RuntimeException(
                    "ERROR: FILA_ESTUDIANTE debe ser A, B, C o D. Valor actual: " + FILA_ESTUDIANTE);
        }
    }

    @Test
    @DisplayName("Test 1: Validar getTotalValidYears() - Cálculo de años válidos")
    public void testGetTotalValidYears() {
        // Obtener datos de prueba
        Brand[] brands = TestData.createBrands();

        // Validar el cálculo de años válidos usando el validador
        Validators.validarCalculoTotalValidYears(brands);
    }

    @Test
    @DisplayName("Test 2: Validar método de ordenamiento según fila asignada")
    public void testMetodoOrdenamiento() {
        String fila = FILA_ESTUDIANTE.trim().toUpperCase();

        // Obtener datos originales
        Brand[] original = TestData.createBrands();
        Brand[] paraOrdenar = Validators.copiarArregloBrands(original);

        // Ejecutar ordenamiento según la fila usando reflexión
        if (fila.equals("A")) {
            // Fila A: Selection Sort Descendente
            invocarMetodoOrdenamiento("sortSelectionDesc", paraOrdenar);
            Validators.validarNoNulos(paraOrdenar);
            Validators.validarIntegridadDatos(original, paraOrdenar);
            Validators.validarOrdenamientoDescendente(paraOrdenar);

        } else if (fila.equals("B")) {
            // Fila B: Selection Sort Ascendente
            invocarMetodoOrdenamiento("sortSelectionAsc", paraOrdenar);
            Validators.validarNoNulos(paraOrdenar);
            Validators.validarIntegridadDatos(original, paraOrdenar);
            Validators.validarOrdenamientoAscendente(paraOrdenar);

        } else if (fila.equals("C")) {
            // Fila C: Insertion Sort Descendente
            invocarMetodoOrdenamiento("sortInsertionDesc", paraOrdenar);
            Validators.validarNoNulos(paraOrdenar);
            Validators.validarIntegridadDatos(original, paraOrdenar);
            Validators.validarOrdenamientoDescendente(paraOrdenar);

        } else if (fila.equals("D")) {
            // Fila D: Bubble Sort Descendente
            invocarMetodoOrdenamiento("sortBubbleDesc", paraOrdenar);
            Validators.validarNoNulos(paraOrdenar);
            Validators.validarIntegridadDatos(original, paraOrdenar);
            Validators.validarOrdenamientoDescendente(paraOrdenar);
        }
    }

    @Test
    @DisplayName("Test 3: Validar búsqueda binaria según criterios de fila")
    public void testBusquedaBinariaPorFila() {
        String fila = FILA_ESTUDIANTE.trim().toUpperCase();

        // Obtener datos originales
        Brand[] brands = TestData.createBrands();
        Brand[] paraOrdenar = Validators.copiarArregloBrands(brands);

        if (fila.equals("A")) {
            // Fila A: Selection Sort Descendente
            invocarMetodoOrdenamiento("sortSelectionDesc", paraOrdenar);

            // Búsqueda 1: 8 años (existe) - ordenado descendente
            Brand resultado1 = invocarBusquedaBinaria(paraOrdenar, 8, false);
            Validators.validarBusquedaExitosa(resultado1, 8);

            // Búsqueda 2: 10 años (no existe) - ordenado descendente
            Brand resultado2 = invocarBusquedaBinaria(paraOrdenar, 10, false);
            Validators.validarBusquedaFallida(resultado2, 10);

        } else if (fila.equals("B")) {
            // Fila B: Selection Sort Ascendente
            invocarMetodoOrdenamiento("sortSelectionAsc", paraOrdenar);

            // Búsqueda 1: 7 años (existe) - ordenado ascendente
            Brand resultado1 = invocarBusquedaBinaria(paraOrdenar, 7, true);
            Validators.validarBusquedaExitosa(resultado1, 7);

            // Búsqueda 2: 5 años (no existe) - ordenado ascendente
            Brand resultado2 = invocarBusquedaBinaria(paraOrdenar, 5, true);
            Validators.validarBusquedaFallida(resultado2, 5);

        } else if (fila.equals("C")) {
            // Fila C: Insertion Sort Descendente
            invocarMetodoOrdenamiento("sortInsertionDesc", paraOrdenar);

            // Búsqueda 1: 6 años (existe) - ordenado descendente
            Brand resultado1 = invocarBusquedaBinaria(paraOrdenar, 6, false);
            Validators.validarBusquedaExitosa(resultado1, 6);

            // Búsqueda 2: 9 años (no existe) - ordenado descendente
            Brand resultado2 = invocarBusquedaBinaria(paraOrdenar, 9, false);
            Validators.validarBusquedaFallida(resultado2, 9);

        } else if (fila.equals("D")) {
            // Fila D: Bubble Sort Descendente
            invocarMetodoOrdenamiento("sortBubbleDesc", paraOrdenar);

            // Búsqueda 1: 7 años (existe) - ordenado descendente
            Brand resultado1 = invocarBusquedaBinaria(paraOrdenar, 7, false);
            Validators.validarBusquedaExitosa(resultado1, 7);

            // Búsqueda 2: 4 años (no existe) - ordenado descendente
            Brand resultado2 = invocarBusquedaBinaria(paraOrdenar, 4, false);
            Validators.validarBusquedaFallida(resultado2, 4);
        }
    }
}
