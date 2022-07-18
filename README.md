### Setup

It is recommended that you clone this repository from Github to your local machine.

Otherwise, Github remote address needs to be updated to your local machine:
`git remote set-url origin git@github.com:One-Click-LCA/360Optimi.git`

It means bye bye the repository on our VPN. 

Double check if the remote has been updated succcessfully by:
`git remote -v`

The result should be something like this:
```
origin  git@github.com:One-Click-LCA/360Optimi.git (fetch)
origin  git@github.com:One-Click-LCA/360Optimi.git (push)
```

For Github to grant your machine access, you need to generate a public key and add it to your Github account. This only need to be done once. Follow instructions [here](https://docs.github.com/en/free-pro-team@latest/github/authenticating-to-github/adding-a-new-ssh-key-to-your-github-account).

### Rule of thumb
* The `master` branch is for PROD.
* The `bugs` branch is for your bugz.
* The `features` branch is for development. **We will mainly make Pull Requests to merge to this branch**.
* Every developer should have a separated branch, work on their branch and make PRs to merge to branch `dev`. 
* For big features, the feature could have its own branch, e.g. branch `features/some-awesome-feature`.

**Note:** a PR basically shows the difference between two branches, which is in our case: your branch and branch dev. It means you can finish multiple tickets, make commits and push to your branch. The PR will show everything. **Once the PR is approved, please resolve conficts (if any) and merge right away before pushing new commits**. It will help the reviewers not to review previously approved codes. They will thank you! 

### Steps to make a Pull Request
* If you are working with multiple developers on your branch, make sure it is up-to-date before checking out to `dev`.
* Make sure `dev` is up-to-date.
* Merge `dev` to `your branch` and resolve conflicts (if any).
* Push to your branch on Github.
* Open a PR.
* After an approval, you or reviewer can click merge to `dev` on Github.

**Commands to do the above, start from your branch:**
```
git pull --rebase
git checkout dev
git pull
git checkout <your-branch-name>
git merge dev
< Resolve conflicts if any >
git push
< Open a Pull Request >
```
After opening the Pull Request, if some changes need to be made, just do it, make new commits and repeat the steps again. 

i.e.`pull dev -> merge dev to your branch -> resolve conflicts (if any) -> push to your branch`.

### Some useful commands

To create a new branch: `git checkout -b <branch-name>`

To check which branch you are on, run: `git branch`

When a new branch is created on local, it needs to be set to keep track of the branch on remote, run:
`git push --set-upstream origin <your-branch-name>`

### Rough Steps for Intellij integration
Intellij:
File > Settings > Plugins
Find and tick Git and Github plugin and then install the Plugins
It'll ask you to link your github and intellij, you'll need to follow the steps and allow autherization.
Afterwards, you can go to:
Git > Github > View pull requests
