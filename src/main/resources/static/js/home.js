function resetFilters() {
    console.log('Reset filters called'); // Debugging-Log
    const url = new URL(window.location.href);
    url.searchParams.delete('query');
    url.searchParams.delete('sort');
    url.searchParams.delete('category');
    url.searchParams.delete('minPrice');
    url.searchParams.delete('maxPrice');
    url.searchParams.delete('hasUebertopf');
    url.searchParams.delete('minHeight');
    url.searchParams.delete('maxHeight');
    window.location.href = url.toString();
}

document.addEventListener('DOMContentLoaded', function() {
    const resetButton = document.querySelector('#resetFilters');
    if (resetButton) {
        resetButton.addEventListener('click', resetFilters);
    }
});

function createImageGallery(images) {
    let currentIndex = 0;
    const mainImg = document.getElementById('main-img');
    const prevBtn = document.getElementById('prev-btn');
    const nextBtn = document.getElementById('next-btn');

    if (!mainImg || !prevBtn || !nextBtn || images.length === 0) {
        return;
    }

    mainImg.src = images[currentIndex];

    prevBtn.addEventListener('click', () => {
        currentIndex = (currentIndex > 0) ? currentIndex - 1 : images.length - 1;
        mainImg.src = images[currentIndex];
    });

    nextBtn.addEventListener('click', () => {
        currentIndex = (currentIndex < images.length - 1) ? currentIndex + 1 : 0;
        mainImg.src = images[currentIndex];
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const images = [
        "path/to/image1.jpg",
        "path/to/image2.jpg",
        "path/to/image3.jpg"
    ];
    createImageGallery(images);
});