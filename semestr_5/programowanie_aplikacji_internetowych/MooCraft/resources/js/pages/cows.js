const totalColors = 5;
initColorDivs();

function initColorDivs() {
    showColorDiv(0);

    for (let index = 0; index < totalColors; index++) {
        const checkbox = document.getElementById(`color${index}_choose`);
        const inputs = getColorInputs(index);

        handleEnablingInputs(checkbox, inputs);
        handleShowingNextColorDiv(index, checkbox);
    }
}

function getColorInputs(index) {
    const valueInput = document.getElementById(`color${index}_value`);
    const weightInput = document.getElementById(`color${index}_weight`);
    return [valueInput, weightInput];
}

function handleEnablingInputs(checkbox, inputs) {
    enableInputs(inputs, checkbox.checked);
    checkbox.addEventListener('change', () => enableInputs(inputs, checkbox.checked));
}

function enableInputs(inputs, enable) {
    inputs.forEach(input => input.disabled = !enable);
}

function handleShowingNextColorDiv(currentIndex, checkbox) {
    if (currentIndex >= totalColors - 1) return;

    if (checkbox.checked) {
        showColorDiv(currentIndex + 1);
    } else {
        checkbox.addEventListener('change', () => showColorDiv(currentIndex + 1), {once: true});
    }
}

function showColorDiv(index) {
    const colorDiv = document.getElementById(`color${index}`);
    colorDiv.classList.remove('hidden');
}
