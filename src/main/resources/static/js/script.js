function sortBooksAndRedirect(criteria) {
    // Удаляем класс 'active' у всех кнопок
    const buttons = document.querySelectorAll('.sort-option');
    buttons.forEach(button => {
        button.classList.remove('active');
    });

    // Добавляем класс 'active' к нажатой кнопке
    const activeButton = document.querySelector(`.sort-option[onclick="sortBooksAndRedirect('${criteria}')"]`);
    activeButton.classList.add('active');

    // Генерируем URL с параметром 'sort'
    const url = new URL(window.location.href);
    url.searchParams.set('sort', criteria);
    url.searchParams.set('page', 1);

    // Переходим по новому URL
    window.location.href = url.toString();
}

// Проверяем URL при загрузке страницы и устанавливаем класс 'active'
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const sortCriteria = urlParams.get('sort');
    if (sortCriteria) {
        const activeButton = document.querySelector(`.sort-option[onclick="sortBooksAndRedirect('${sortCriteria}')"]`);
        if (activeButton) {
            activeButton.classList.add('active');
        }
    }
});

function changeButtonState(button) {
    button.style.backgroundColor = "#ffffff";
    button.style.color = "#b19576";
    button.style.border = "1px solid #b19576";
    button.textContent = "В корзине";
    button.onclick = function() {
            window.location.href = '/cart';
        };
}

