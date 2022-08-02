import pathlib

from fastai.learner import load_learner

pathlib.PosixPath = pathlib.WindowsPath
from pathlib import Path

loaded_learner = load_learner(
    Path(__file__).resolve().parent / "model" / "classify.pkl"
)


def predict(image_path) -> list[str]:
    _, _, probs = loaded_learner.predict(image_path)
    result = {
        loaded_learner.dls.vocab[idx]: float(prob)
        for idx in range(0, len(probs))
        if (prob := probs[idx]) > 0.5
    }
    return result if result else {"Unknown": 100}
