from kivy.app import App
from kivy.uix.widget import Widget
from kivy.properties import NumericProperty, ReferenceListProperty, ObjectProperty
from kivy.vector import Vector
from kivy.clock import Clock

from random import randint


class PongPaddle(Widget):
    score = NumericProperty(0)

    def bounce_ball(self, ball):
        if self.collide_widget(ball):
            vx, vy = ball.velocity
            offset = (ball.center_y - self.center_y) / (self.height / 2)
            bounced = Vector(-1 * vx, vy) * 1.1
            ball.velocity = bounced.x, bounced.y + offset


class PongGame(Widget):
    ball = ObjectProperty(None)
    lp = ObjectProperty(None)
    rp = ObjectProperty(None)

    def update(self, dt):
        self.ball.move()
        self.lp.bounce_ball(self.ball)
        self.rp.bounce_ball(self.ball)
        self.ball.bounce()

        if self.ball.x < self.x:
            self.rp.score += 1
            self.serve_ball()
        if self.ball.x > self.width:
            self.lp.score += 1
            self.serve_ball()
    
    def on_touch_move(self, touch):
        if touch.x < self.width / 3:
            self.lp.center_y = touch.y
        if touch.x > self.width * 2 / 3:
            self.rp.center_y = touch.y

    def serve_ball(self, velocity=(4, 4)):
        self.ball.center = self.center
        self.ball.velocity = Vector(*velocity).rotate(randint(0, 360))


class PongBall(Widget):
    velocity_x = NumericProperty(0)
    velocity_y = NumericProperty(0)

    velocity = ReferenceListProperty(velocity_x, velocity_y)

    def move(self):
        self.pos = Vector(*self.velocity) + self.pos

    def bounce(self):
        if self.y < 0 or self.top > self.parent.height:
            self.velocity_y *= -1


class PongApp(App):
    def build(self):
        game = PongGame()
        game.serve_ball()
        Clock.schedule_interval(game.update, 1.0 / 60.0)
        return game


if __name__ == "__main__":
    PongApp().run()
