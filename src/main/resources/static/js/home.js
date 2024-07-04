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
