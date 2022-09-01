/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notacioninfijapostfija;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class NotacionInfijaPostfija {

    
    
       
    
    
    public static void main(String[] args) {
        
        Scanner entrada = new Scanner(System.in);
        
        ArrayList<ArrayList> valoresOperadores = new ArrayList();
        
        //Agrego 3 sublistas (operadores, peso de expresion, peso de pila) a mi lista.
        for(int i=0; i<3; i++) {
            valoresOperadores.add(new ArrayList());
        }
        
        //Valores primera sublista (operadores).
        valoresOperadores.get(0).add('^');
        valoresOperadores.get(0).add('*');
        valoresOperadores.get(0).add('/');
        valoresOperadores.get(0).add('+');
        valoresOperadores.get(0).add('-');
        valoresOperadores.get(0).add('(');
        valoresOperadores.get(0).add(')');
        
        //Valores segunda sublista (peso en expresion).
        valoresOperadores.get(1).add(4); //^
        valoresOperadores.get(1).add(2); //*
        valoresOperadores.get(1).add(2); // /
        valoresOperadores.get(1).add(1); // +
        valoresOperadores.get(1).add(1); // -
        valoresOperadores.get(1).add(5); // (
        valoresOperadores.get(1).add(null); // )
        
        //Valores tercera sublista (peso en pila).
        valoresOperadores.get(2).add(3); // ^
        valoresOperadores.get(2).add(2); // *
        valoresOperadores.get(2).add(2); // /
        valoresOperadores.get(2).add(1); // +
        valoresOperadores.get(2).add(1); // -
        valoresOperadores.get(2).add(0); // (
        valoresOperadores.get(2).add(null); // )
        
        
/*******************CONVERSIÓN DE INFIJA A POSTFIJA***************************/
        System.out.println("/********** CONVERSIÓN EXPRESIÓN INFIJA A POSTFIJA MÁS EL RESULTADO **********/");

        //Crear una pila vacía de operandos.
        Stack<String> operandos = new Stack();
        
        //Realice pruebas con estas expresiones infijas.
        //String dato = "7-(2*3)+3";
        //String dato = "2+3";
        //String dato = "7+5/2";
        //String dato = "(7+5)/2";
        //String dato = "(7+5)/2^2";
        
        String dato;
        
        //Se pide la expresión al usuario.
        System.out.print("Escribe tu expresión infija: ");
        dato = entrada.next();
        
        
        
        //Crear un string vacío, llamado npostfija.
        String npostfija = "";
        
        //Validar que no sean letras o algún otro simbolo diferente a los operadores.
        
        char letra;
        boolean validar;
        
        for(int i=0; i<dato.length(); i++) {
            
            do{
                letra = dato.charAt(i);
                try {
                    Float.parseFloat(Character.toString(letra));
                    validar=true;
                } catch (NumberFormatException e) {
                    validar=false;
                }

                if(letra == '^' || letra == '*' || letra == '/' || letra == '+' || letra == '-' || letra == '(' || letra == ')') {
                    validar=true;
                }

                if(validar == false) {
                    System.err.println("ERROR: No has ingresado una expresión infija válida.");
                    System.out.print("Escribe tu expresión infija nuevamente: ");
                    dato = entrada.next();
                }
                
            }while(validar==false);
            
        }
        
        
       
        //Recorriendo el string.
        
        for(int i=0; i<dato.length(); i++) {
            
            
            char c = dato.charAt(i);
            
            
            
            if(c == '^' || c == '*' || c == '/' || c == '+' || c == '-' || c == '(') {
                
                //Si es un operador y esta vacia la pila, lo metemos a la pila.
                if(operandos.isEmpty()) {
                    operandos.push(Character.toString(c));
                } else {
                    int indicePesoExpresion;
                    int indicePesoPila;
                    int pesoExpresion;
                    int pesoPila;
                    char operadorPila = operandos.peek().charAt(0);
                    
                    indicePesoExpresion = valoresOperadores.get(0).indexOf(c);
                    indicePesoPila = valoresOperadores.get(0).indexOf(operadorPila);
                    
                    pesoExpresion = (int) valoresOperadores.get(1).get(indicePesoExpresion);
                    pesoPila = (int) valoresOperadores.get(2).get(indicePesoPila);
                    
                    //Si el operador es mayor al operador de la pila.
                    if(pesoExpresion > pesoPila) {
                        
                        operandos.push(Character.toString(c));
                    } else {
                        //Si el operador es igual operador de la pila.
                        if(pesoExpresion == pesoPila) {
                            npostfija += operandos.pop();
                            operandos.push(Character.toString(c));
                        } else {
                            //Si el operador es menor al operador de la pila.
                            while(!operandos.isEmpty()) {
                                npostfija += operandos.pop();
                                
                            }
                            
                            operandos.push(Character.toString(c));
                        } 
                    }
                    
                }
                //Si es )
            } else if(c == ')') {
                    while(!operandos.isEmpty()) {
                        String valor = operandos.pop();
                        if(!"(".equals(valor)) {
                            npostfija += valor;
                        } else {
                            break;
                        }
                    }
                    
                    
                } else {
                npostfija += c;
            }
                
        }
        
        
        //Vaciamos todos los operadores que quedaron en la pila.
        while(!operandos.isEmpty()) {
            npostfija += operandos.pop();
        }
        
        System.out.println("Expresión infija: " + dato);
        System.out.println("Conversión a expresión postfija: " + npostfija);
/******************FIN DE LA CONVERSIÓN DE INFIJA A POSTFIJA******************/
        
        
        
/*********************EVALUAR LA EXPRESIÓN POSTFIJA***************************/
        Stack<String> operandos2 = new Stack();
        float operando1, operando2;
        
        /*
            Crear una pila vacía de operandos. listo
            Declarar dos variables, operando1 y operando2. listo
            Ir recorriendo la expresión postfija y preguntar si es un operando.
            En caso de que sí lo sea, meterlo a la pila.
            En caso de que no serlo:
             Sacar el tope de la pila y asignarlo a la variable operando2.
             Volver a sacar el tope de la pila y asignarlo a la variable operando1. Es importante hacerlo en ese orden.
             Hacer la operación del operador y meter el resultado a la pila.
            Una vez que se acabe de recorrer el resultado, la operación estará en el tope de la pila.
        
        */
        
        
        for(int i=0; i<npostfija.length(); i++) {
            
            char c2 = npostfija.charAt(i);
            
            
            
            if(c2 == '^' || c2 == '*' || c2 == '/' || c2 == '+' || c2 == '-') {
                operando2 = Float.parseFloat(operandos2.pop());
                operando1 = Float.parseFloat(operandos2.pop());
                
                
                
                float resultado = 0;
                
                switch(c2) {
                    
                    case '^':
                        resultado = (float) (Math.pow(operando1, operando2));
                    
                    case '*':
                        resultado = operando1 * operando2;
                        break;
                        
                    case '/':
                        resultado = operando1 / operando2;
                        break;
                        
                    case '+':
                        resultado = operando1 + operando2;
                        break;
                        
                        
                    case '-':
                        resultado = operando1 - operando2;
                        break;
                }
                
                operandos2.push(Float.toString(resultado));
                
            } else {
              
                operandos2.push(Character.toString(c2));

            }
        }
        
        System.out.println("El resultado es: " + operandos2.pop());
/**************************FIN DE LA EVALUACIÓN*******************************/
      
        

    }
    
}
