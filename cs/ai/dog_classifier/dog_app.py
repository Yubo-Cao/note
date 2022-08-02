from pathlib import Path
from threading import Thread
from tkinter import filedialog

from kivy.properties import ObjectProperty, StringProperty
from kivymd.app import MDApp
from kivymd.uix.behaviors import RoundedRectangularElevationBehavior
from kivymd.uix.card import MDCard
from kivymd.uix.chip import MDChip
from kivymd.uix.screen import MDScreen


class ClassifyDogCard(MDCard, RoundedRectangularElevationBehavior):
    DEFAULT_IMAGE = Path(__file__).resolve().parent / "view" / "place_holder.png"
    dog_image = StringProperty(str(DEFAULT_IMAGE))
    chips = ObjectProperty(None)

    def classify(self):
        if self.dog_image == str(self.DEFAULT_IMAGE):
            self.pick_image()
        result = self.parent.predict(self.dog_image)
        self.chips.clear_widgets()
        for type, prob in result.items():
            self.chips.add_widget(MDChip(text=f"{type} {prob:.2f}"))

    def clear(self):
        self.dog_image = str(ClassifyDogCard.DEFAULT_IMAGE)
        self.chips.clear_widgets()

    def pick_image(self):
        path = filedialog.askopenfile(
            title="Open a dog image",
            initialdir="/",
            filetypes=(("image files", "*.png"), ("image files", "*.jpg")),
        )
        if path:
            self.dog_image = (
                path.name
                if path.name and Path(path.name).exists()
                else str(ClassifyDogCard.DEFAULT_IMAGE)
            )


class ClassifyDog(MDScreen):
    banner = ObjectProperty(None)

    def __init__(self):
        super(ClassifyDog, self).__init__()

        def load_predict(self):
            try:
                from classify import predict
            except Exception as e:
                self.banner.text = ["Error", repr(e)]
                self.banner.show()
            self.predict = lambda path: predict(path)

        Thread(target=load_predict, args=(self,)).start()

    def predict(self, image_path):
        self.banner.show()


class DogApp(MDApp):
    def build(self):
        return ClassifyDog()


if __name__ == "__main__":
    DogApp().run()
