import random

# Gera um teste aleatório com as dimensões da caixa e dos pacotes dentro dos intervalos especificados
def generate_random_test(num_packages, box_x_range, box_y_range, package_x_range, package_y_range):
    # Gera as dimensões da caixa
    box_x = random.randint(*box_x_range)
    box_y = random.randint(*box_y_range)

    # Gera os pacotes aleatórios
    packages = []
    for i in range(1, num_packages + 1):
        package_x = random.randint(*package_x_range)
        package_y = random.randint(*package_y_range)
        packages.append((i, package_x, package_y))

    return box_x, box_y, packages

def save_test_to_file(file_path, box_x, box_y, packages):
    with open(file_path, 'w') as file:
        # Salva as dimensões da caixa na primeira linha
        file.write(f"{box_x} {box_y}\n")

        # Salva os pacotes nas linhas seguintes
        for package in packages:
            file.write(" ".join(map(str, package)) + '\n')

# Exemplo de uso:
num_packages = 50
box_x_range = (8, 16)
box_y_range = (8, 16)
package_x_range = (6, 12)
package_y_range = (6, 12)

file_path = "Testes/teste" + str(num_packages) + ".txt"
test_data = generate_random_test(num_packages, box_x_range, box_y_range, package_x_range, package_y_range)
save_test_to_file(file_path, *test_data)
