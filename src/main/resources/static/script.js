function getAvailableHours(){
    let dateDom = document.getElementById('date');
    dateDom.addEventListener('change',(event) =>{
        console.log("works!")
        console.log(document.getElementById('date').value);
        let date = document.getElementById('date').value;

        const xhr = new XMLHttpRequest();
        // Initialize the request
        xhr.open("GET", `/getAvailableHours?date=${date}`);
        // Send the request
        xhr.send();
        // Fired once the request completes successfully
        xhr.onload = function(e) {
            // Check if the request was a success
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                // Get and convert the responseText into JSON
                let response = JSON.parse(xhr.responseText);

                const select_elem = document.getElementById('hour');
                response.forEach(hour => select_elem.add(new Option(hour+':00',hour)));
            }
        }
    })
}
