const { shell, ipcRenderer } = require('electron')

document.querySelector('#open-github').onclick = () => {
  ipcRenderer.send('asynchronous-message', 'github')
  shell.openExternal('https://github.com')
}

document.querySelector('#open-folder').onclick = () => {
  shell.showItemInFolder(__dirname)
}

document.querySelector('#beep').onclick = () => {
  shell.beep()
}

document.querySelector("#boop").onclick = () =>{
  ipcRenderer.send('asynchronous-message', 'boop')
  fetch('http://localhost:8989/favicon.ico').then((data)=>{      
      return data.text()  
  }).then((text)=>{
    ipcRenderer.send('asynchronous-message',text)
  }).catch(e=>{
    ipcRenderer.send('asynchronous-message', e)
  })
}

