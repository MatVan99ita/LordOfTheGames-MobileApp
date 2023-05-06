import sys
from PyQt5.QtGui import QPalette, QLinearGradient, QColor
from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QApplication, QMainWindow, QWidget, QVBoxLayout, QLabel

def hex_to_rgb(hex_color):
    """Converte un colore in esadecimale in RGB"""
    hex_color = hex_color.lstrip('#')
    return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))


def rgb_to_hex(rgb_color):
    return '#' + ''.join('{:02x}'.format(int(c)) for c in rgb_color)


def gradient_colors(base_color):
    # Converte il colore base da esadecimale a RGB
    base_rgb = hex_to_rgb(base_color)
    # Calcola i valori RGB per i tre colori
    dark_rgb = tuple(max(0, c-50) for c in base_rgb)
    light_rgb = tuple(min(255, c+50) for c in base_rgb)
    return [dark_rgb, base_rgb, light_rgb]






def show_gradient(gradient_str1, gradient_str2, gradient_str3, gradient_str4, width=400, height=50):
    gradient1 = gradient_str1
    gradient2 = gradient_str2
    gradient3 = gradient_str3
    gradient4 = gradient_str4

    # Crea l'applicazione e la finestra
    app = QApplication(sys.argv)
    main_window = QMainWindow()
    main_window.setWindowTitle("Gradient")

    # Crea un widget QLabel per visualizzare il gradiente
    gradient_widget1 = QLabel()
    gradient_widget1.setFixedSize(width, height)
    gradient_widget1.setAutoFillBackground(True)
    palette1 = gradient_widget1.palette()

    # Crea un widget QLabel per visualizzare il gradiente
    gradient_widget2 = QLabel()
    gradient_widget2.setFixedSize(width, height)
    gradient_widget2.setAutoFillBackground(True)
    palette2 = gradient_widget2.palette()

    # Crea un widget QLabel per visualizzare il gradiente
    gradient_widget3 = QLabel()
    gradient_widget3.setFixedSize(width, height)
    gradient_widget3.setAutoFillBackground(True)
    palette3 = gradient_widget3.palette()

    # Crea un widget QLabel per visualizzare il gradiente
    gradient_widget4 = QLabel()
    gradient_widget4.setFixedSize(width, height)
    gradient_widget4.setAutoFillBackground(True)
    palette4 = gradient_widget4.palette()

    # Crea un gradiente lineare con i colori del gradient
    grad1 = QLinearGradient(0, 0, width, 0)
    grad2 = QLinearGradient(0, 0, width, 0)
    grad3 = QLinearGradient(0, 0, width, 0)
    grad4 = QLinearGradient(0, 0, width, 0)

    for i in range(3):
        color1 = gradient1[i]
        color2 = gradient2[i]
        color3 = gradient3[i]
        color4 = gradient4[i]
        pos = i / (3 - 1)
        rgb = hex_to_rgb(color1)

        grad1.setColorAt(pos, QColor(*rgb))
        rgb = hex_to_rgb(color2)
        grad2.setColorAt(pos, QColor(*rgb))
        rgb = hex_to_rgb(color3)
        grad3.setColorAt(pos, QColor(*rgb))
        rgb = hex_to_rgb(color4)
        grad4.setColorAt(pos, QColor(*rgb))
    

    # Imposta il gradiente come sfondo del widget
    palette1.setBrush(QPalette.Window, grad1)
    palette2.setBrush(QPalette.Window, grad2)
    palette3.setBrush(QPalette.Window, grad3)
    palette4.setBrush(QPalette.Window, grad4)
    gradient_widget1.setPalette(palette1)
    gradient_widget2.setPalette(palette2)
    gradient_widget3.setPalette(palette3)
    gradient_widget4.setPalette(palette4)

    # Aggiunge il widget alla finestra e mostra la finestra
    layout = QVBoxLayout()
    layout.addWidget(gradient_widget1)
    layout.addWidget(gradient_widget2)
    layout.addWidget(gradient_widget3)
    layout.addWidget(gradient_widget4)
    central_widget = QWidget()
    central_widget.setLayout(layout)
    main_window.setCentralWidget(central_widget)
    main_window.show()

    # Esegui l'applicazione
    sys.exit(app.exec_())


#990000 - rosso
#00ccff - azzurro
#00b300 - verde
#007FFF
#450278 - viola
colors1 = gradient_colors('#990000')
colors2 = gradient_colors('#00ccff')
colors3 = gradient_colors('#00b300')
colors4 = gradient_colors('#450278')

gradient1 = [rgb_to_hex(color) for color in colors1]
gradient2 = [rgb_to_hex(color) for color in colors2]
gradient3 = [rgb_to_hex(color) for color in colors3]
gradient4 = [rgb_to_hex(color) for color in colors4]

print(gradient1)
print(gradient2)
print(gradient3)
print(gradient4)
show_gradient(gradient1, gradient2, gradient3, gradient4)

