import random
import threading
from pathlib import Path
import time
from tkinter import *
from tkinter import messagebox
from tkinter.ttk import *
from typing import List

assets = Path(__file__).resolve().parent / "Classic"


class Card:
    __slot__ = "suit value".split()

    def __init__(self, suit, value) -> None:
        self.suit = suit
        self.value = value

    def __repr__(self):
        return " of ".join((self.value, self.suit))

    @property
    def image(self):
        if res := getattr(self, "_image", None):
            return res
        self._image = PhotoImage(
            file=str(
                assets / (self.suit.lower()[0] + f"{self.numeric_value:02}" + ".png")
            )
        )
        return self._image

    @property
    def numeric_value(self):
        if self.value.isnumeric():
            return int(self.value)
        else:
            return {"A": 1, "J": 11, "Q": 12, "K": 13}[self.value]

    @property
    def image_back(self):
        if res := getattr(Card, "_image_back", None):
            return res
        Card._image_back = PhotoImage(file=str(assets / "back.png"))
        return Card._image_back


class Deck:
    def __init__(self) -> None:
        self.cards = [
            Card(s, v)
            for s in ["Spades", "Clubs", "Hearts", "Diamonds"]
            for v in "A 2 3 4 5 6 7 8 9 10 J Q K".split(" ")
        ]

    def shuffule(self):
        if len(self.cards) > 1:
            random.shuffle(self.cards)

    def deal(self):
        if len(self.cards) > 1:
            return self.cards.pop(0)


class Hand:
    def __init__(self, dealer=False) -> None:
        self.dealer = dealer
        self.cards: List[Card] = []
        self.value = 0

    def add_card(self, card):
        self.cards.append(card)

    def calculate_value(self):
        self.value = 0
        has_ace = False
        for card in self.cards:
            if card.value.isnumeric():
                self.value += int(card.value)
            elif card.value == "A":
                has_ace = True
                self.value += 11
            else:
                self.value += 10
        if has_ace and self.value > 21:
            self.value -= 10

    def get_value(self):
        self.calculate_value()
        return self.value

    def __repr__(self) -> str:
        if self.dealer:
            return f"hidden: {self.cards[0]}"
        else:
            return f"""cards: {', '.join(map(repr, self.cards))} 
value: {self.get_value()}"""


class GameState:
    def __init__(self) -> None:
        self.deck = Deck()
        self.deck.shuffule()

        self.player_hand = Hand()
        self.dealer_hand = Hand(True)

        for _ in range(2):
            self.player_hand.add_card(self.deck.deal())
            self.dealer_hand.add_card(self.deck.deal())

    def hit(self):
        self.player_hand.add_card(self.deck.deal())
        if random.randint(0, 1):
            self.dealer_hand.add_card(self.deck.deal())
        return self.winner

    def stand(self):
        if random.randint(0, 1):
            self.dealer_hand.add_card(self.deck.deal())
        return self.winner

    @property
    def winner(self):
        res = ""
        if self.player_hand.get_value() <= 21 and self.dealer_hand.get_value() <= 21:
            if self.player_hand.get_value() == 21:
                res += "p"
            elif self.dealer_hand.get_value() == 21:
                res += "d"
        else:
            if self.player_hand.get_value() > 21:
                res += "d"
            elif self.dealer_hand.get_value() > 21:
                res += "p"
        return res

    @property
    def player_cards(self):
        return self.player_hand.cards

    @property
    def dealer_cards(self):
        return self.dealer_hand.cards


class GameScreen(Tk):
    def __init__(self):
        super().__init__()
        self.title("Black Jack")
        self.geometry("800x640")
        self.resizable(False, False)

        self.CARD_ORIGINAL_POSITION = 100
        self.CARD_WIDTH_OFFSET = 100
        self.PLAYER_CARD_HEIGHT = 300
        self.DEALER_CARD_HEIGHT = 100

        self.PLAYER_SCORE_TEXT_COORDS = (400, 450)
        self.WINNER_TEXT_COORDS = (400, 250)

        self.game_state = GameState()
        self.game_screen = Canvas(self, width=800, height=500)

        self.bottom_frame = Frame(self, width=800, height=140)
        self.bottom_frame.pack_propagate(0)

        self.htbn = Button(self.bottom_frame, text="Hit", width=25, command=self.hit)
        self.sbtn = Button(
            self.bottom_frame, text="Stick", width=25, command=self.stand
        )

        self.pgbtn = Button(
            self.bottom_frame, text="Play Again", width=25, command=self.again
        )
        self.qbtn = Button(
            self.bottom_frame,
            text="Stick",
            width=25,
            command=lambda: self.after(2000, self.destroy())
            if messagebox.askyesno("Black Jack", "Do you want to quit?")
            else None,
        )

        self.htbn.pack(side=LEFT, padx=100)
        self.sbtn.pack(side=RIGHT, padx=100)

        self.bottom_frame.pack(side=BOTTOM, fill=X)
        self.game_screen.pack(side=LEFT, anchor=N)
        self.display_table()

    def again(self):
        pass

    def display_table(self, hide_dealer=True):
        self.game_screen.delete("all")
        for offset, player_card in enumerate(self.game_state.player_cards):
            self.game_screen.create_image(
                (
                    self.CARD_ORIGINAL_POSITION + self.CARD_WIDTH_OFFSET * offset,
                    self.PLAYER_CARD_HEIGHT,
                ),
                image=player_card.image,
            )

        if hide_dealer and not self.game_state.winner:
            self.game_screen.create_image(
                (self.CARD_ORIGINAL_POSITION, self.DEALER_CARD_HEIGHT),
                image=self.game_state.dealer_cards[0].image_back,
            )

        for offset, dealer_card in enumerate(
            self.game_state.dealer_cards[int(hide_dealer) :], start=int(hide_dealer)
        ):
            self.game_screen.create_image(
                (
                    self.CARD_ORIGINAL_POSITION + self.CARD_WIDTH_OFFSET * offset,
                    self.DEALER_CARD_HEIGHT,
                ),
                image=dealer_card.image,
            )

        if w := self.game_state.winner:
            msg = ""
            if len(w) == 2:
                msg = "TIE"
            elif w == "p":
                msg = "You WIN"
            elif w == "d":
                msg = "You LOSE"

            self.game_screen.create_text(
                self.WINNER_TEXT_COORDS, text=msg, font=(None, 50)
            )
    
    def hit(self):
        self.game_state.hit()
        self.display_table()
    
    def stand(self):
        self.game_state.stand()
        self.display_table(False)


GameScreen().mainloop()
