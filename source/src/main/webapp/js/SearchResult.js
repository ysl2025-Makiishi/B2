document.addEventListener("DOMContentLoaded", function () {
  const btn = document.getElementById("scrollToTopBtn");
  btn.addEventListener("click", function () {
    window.scrollTo({ top: 0, behavior: "smooth" });
  });
});