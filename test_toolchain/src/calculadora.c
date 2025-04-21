#include <operaciones.h>
#include <stdio.h>

int main()
{

  char operation;
  double first, second;
  printf("Seleccione una operacion (+, -, *, /, c, s): ");
  scanf("%c", &operation);
  printf("Ingrese el primer operando: ");
  scanf("%lf", &first);
  printf("Ingrese el segundo operando: ");
  scanf("%lf", &second);

  switch (operation)
  {
  case '+':
    printf("%.1lf + %.1lf = %.1lf \n", first, second, add(first, second));
    break;
  case '-':
    printf("%.1lf - %.1lf = %.1lf \n", first, second, substract(first, second));
    break;
  case '*':
    printf("%.1lf * %.1lf = %.1lf \n", first, second, mutiply(first, second));
    break;
  case '/':
    if (second != 0)
      printf("%.1lf / %.1lf = %.1lf \n", first, second, divide(first, second));
    else
      printf("Error: division entre 0 \n");
    break;
  case 'c':
    printf("cos(%.1lf) = %.1lf \n", first, cosine(first));
    break;
  case 's':
    printf("square root(%.1lf) = %.1lf \n", first, square_root(first));
    break;
  // operator doesn't match any case constant
  default:
    printf("Operacion no soportada");
  }

  return 0;
}