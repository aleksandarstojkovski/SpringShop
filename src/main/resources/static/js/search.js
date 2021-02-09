document.addEventListener("DOMContentLoaded",function (){

    const searchButton = document.getElementById("searchButton");
    const searchInput = document.getElementById("searchInput");
    const searchInfo = document.getElementById("searchInfo");
    const context = document.querySelector('base').getAttribute('href');
    const searchApi = "api/items/search?q=";

    searchButton.addEventListener("click", function (){
        search();
    })

    searchInput.addEventListener("keyup", function (){
        if (searchInput.value.length > 2){
            search();
        }
        if (searchInput.value.length === 0){
            search();
        }
    })

    function search(){
        const url = context + searchApi + searchInput.value;
        const options = {method: "GET"};
        fetch(url,options)
            .then(function (response){
                return response.json();

            })
            .then(function (elements){
                const offerContainer = document.getElementById("offerContainer");
                const requestContainer = document.getElementById("requestContainer");
                offerContainer.innerHTML="";
                requestContainer.innerHTML="";
                let offerContainerContent = "";
                let requestContainerContent = "";
                let element = "";
                searchInfo.innerText = elements.length + " article(s) found";
                for (let i = 0; i < elements.length; i++) {
                    const date = new Date(elements[i].date);
                    const dateFormatted = date.getDate() + "." + (date.getMonth()+1) + "." + date.getFullYear();
                    const title = elements[i].title;
                    const id = elements[i].id;
                    let description = elements[i].description;
                    if (description.length > 39){
                        description = description.substr(0,36);
                        description += "...";
                    }
                    const type = elements[i].type.name;
                    const itemLink = context + 'item/' + id;
                    const imageLink = context + 'item/' + id + '/image';
                    const editLink = context + 'item/' + id + '/edit';
                    const deleteLink = context + 'item/' + id + '/delete';

                    element = '<div class="col-md-4">\n' +
                        '    <div class="card mb-4 box-shadow">\n' +
                        '        <img style="max-height: 200px;float: left;object-fit: cover;" class="card-img-top" src="'+imageLink+'" alt="Image">\n' +
                        '        <div class="card-body">\n' +
                        '            <h5><a href="'+itemLink+'" aria-label="Item name: '+title+'">'+title+'</a></h5>\n' +
                        '            <p class="card-text" aria-label="Item description: '+description+'" tabindex="0">'+description+'</p>\n' +
                        '            <div class="d-flex justify-content-between align-items-center">\n' +
                        '                <div class="btn-group">\n' +
                        '                    <a href="'+itemLink+'"><button type="button" class="btn btn-sm btn-outline-secondary" tabindex="0" aria-label="View Item">View</button></a>\n' +
                        '                    <!--<a sec:authorize="isAuthenticated()" href="'+editLink+'"><button type="button" class="btn btn-sm btn-outline-secondary">Edit</button></a>-->\n' +
                        '                    <!--<a sec:authorize="hasRole(\'ADMIN\')" href="'+deleteLink+'"><button type="button" class="btn btn-sm btn-outline-secondary">Delete</button></a>-->\n' +
                        '                </div>\n' +
                        '                <small class="text-muted" datetime="YYYY-MM-DD" tabindex="0" aria-label="Item published on: '+dateFormatted+'">'+dateFormatted+'</small>\n' +
                        '            </div>\n' +
                        '        </div>\n' +
                        '    </div>\n' +
                        '</div>';

                    if (type === "Request")
                        requestContainerContent += element;
                    else
                        offerContainerContent += element;

                }
                requestContainer.innerHTML += requestContainerContent;
                offerContainer.innerHTML += offerContainerContent;
            })
    }

    searchButton.click();

})